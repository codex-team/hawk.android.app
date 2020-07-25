package so.codex.uicomponent.edittext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import kotlinx.android.synthetic.main.view_edit_text.view.card_background
import kotlinx.android.synthetic.main.view_edit_text.view.title_edit_text
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate

/**
 * Implementation of custom editView with title and background. Used like as composition of complex
 * view
 * @author Shiplayer
 */
class EditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.codexEditTextTheme
) : FrameLayout(context, attrs, defStyleAttr) {
    private val editView: android.widget.EditText

    init {
        val view = inflate(context, R.layout.view_edit_text, this)

        editView = view.findViewById(R.id.body_edit_text)

        context.obtainStyledAttributes(
            attrs,
            R.styleable.EditText,
            defStyleAttr,
            R.style.Codex_Widgets_EditTextTheme
        ).apply {

            editView.setText(getString(R.styleable.EditText_codex_text) ?: "")
            editView.hint = getString(R.styleable.EditText_codex_hint) ?: ""
            title_edit_text.text = getString(R.styleable.EditText_codex_title) ?: ""
            editView.inputType = when (getInt(R.styleable.EditText_codex_inputType, 0)) {
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else -> {
                    Log.w(EditText::class.java.simpleName, "Current input type not supported!")
                    InputType.TYPE_NULL
                }
            }
            if (hasValue(R.styleable.EditText_codex_titleColor)) {
                title_edit_text.setTextColor(getColorOrThrow(R.styleable.EditText_codex_titleColor))
            }
            if (hasValue(R.styleable.EditText_codex_textColor)) {
                editView.setTextColor(getColorOrThrow(R.styleable.EditText_codex_textColor))
            }
            if (!getBoolean(R.styleable.EditText_codex_hasSpacing, true)) {
                editView.filters = editView.filters.toMutableList().apply {
                    add(InputFilter { source, start, end, dest, dstart, dend ->
                        if (source.contains("\\s+".toRegex())) {
                            source.replace("\\s+".toRegex(), "")
                        } else {
                            null
                        }
                    })
                }.toTypedArray()
            }
            recycle()
        }
        editView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                card_background.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.edit_text_background_selected
                    )
                )
            } else {
                card_background.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.edit_text_background
                    )
                )
            }
        }
    }

    /**
     * Add filter to [editView]
     * @param inputFilter Filter for text
     */
    fun addFilter(inputFilter: InputFilter) {
        editView.filters = editView.filters.toMutableList().apply {
            add(inputFilter)
        }.toTypedArray()
    }

    /**
     * Text that changing in view and can set from something. Used delegate for that.
     */
    public var text: String by textViewDelegate(editView)


    /**
     * Methods and class for saving instance state after changing system settings
     */
    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            text = editView.text.toString()
            id = getId()
            requestFocus = editView.isFocused
            if (editView.isFocused)
                position = editView.selectionStart
        }
    }

    /**
     * Restore state fields of view and fill it from Parcelable
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            if (id == state.id) {
                editView.post {
                    editView.setText(state.text)
                    if (state.requestFocus) {
                        editView.requestFocus()
                        editView.setSelection(state.position)
                    }
                }
            }
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    /**
     * Class that contain information about fields of current view
     */
    class SavedState : BaseSavedState {
        /**
         * Searched text
         */
        var text: String = ""
        /**
         * Id of current view
         */
        var id: Int = -1
        /**
         * View was focused
         */
        var requestFocus = false
        /**
         * Current position of cursor
         */
        var position = text.length

        /**
         * Constructor with reading data from parcel and fill fields
         */
        constructor(source: Parcel?) : super(source) {
            text = source?.readString() ?: ""
            id = source?.readInt() ?: -1
            requestFocus = (source?.readByte() ?: 0.toByte()) != 0.toByte()
            position = source?.readInt() ?: text.length
        }

        /**
         * That same that constructor above
         */
        constructor(parcelable: Parcelable?) : super(parcelable)

        /**
         * Save fields in parcel for saving state
         */
        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
            out?.writeInt(id)
            out?.writeByte(if (requestFocus) 1 else 0)
            out?.writeInt(position)
        }

        /**
         * describe contents
         */
        override fun describeContents(): Int {
            return 0
        }

        /**
         * Creator used for loading and saving fields
         */
        companion object CREATOR : Parcelable.Creator<SavedState> {
            /**
             * Create a new instance of the Parcelable class, instantiating it
             * from the given Parcel whose data had previously been written
             */
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            /**
             * Create a new array of the Parcelable class.
             */
            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
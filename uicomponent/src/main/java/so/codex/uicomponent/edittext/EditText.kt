package so.codex.uicomponent.edittext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_edit_text.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate

/**
 * Реализация EditText с лейблом и задним фоном, который подсвечивается во время фокуса
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
            /*context.theme.obtainStyledAttributes(arrayOf(android.R.attr.editTextColor, android.R.attr.textColor).toIntArray()).apply {
                body_edit_text.setTextColor(
                    getColor(
                        R.styleable.EditText_codex_textColor,
                        ContextCompat.getColor(context, android.R.color.black)
                    )
                )
                title_edit_text.setTextColor(
                    getColor(
                        R.styleable.EditText_codex_titleColor,
                        ContextCompat.getColor(context, android.R.color.black)
                    )
                )
                recycle()
            }*/
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

    public var text: String by textViewDelegate(editView)

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            text = editView.text.toString()
            id = getId()
            requestFocus = editView.isFocused
            if(editView.isFocused)
                position = editView.selectionStart
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            if (id == state.id) {
                editView.post {
                    editView.setText(state.text)
                    if(state.requestFocus){
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


    class SavedState : BaseSavedState {
        var text: String = ""
        var id: Int = -1
        var requestFocus = false
        var position = text.length

        constructor(source: Parcel?) : super(source) {
            text = source?.readString() ?: ""
            id = source?.readInt() ?: -1
            requestFocus = (source?.readByte() ?: 0.toByte()) != 0.toByte()
            position = source?.readInt() ?: text.length
        }

        constructor(parcelable: Parcelable?) : super(parcelable)

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
            out?.writeInt(id)
            out?.writeByte(if(requestFocus) 1 else 0)
            out?.writeInt(position)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
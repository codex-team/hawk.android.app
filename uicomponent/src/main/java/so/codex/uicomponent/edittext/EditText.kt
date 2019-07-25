package so.codex.uicomponent.edittext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_edit_text.view.*
import so.codex.uicomponent.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.codexEditTextTheme
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.view_edit_text, this)

        context.obtainStyledAttributes(
                attrs,
                R.styleable.EditText,
                defStyleAttr,
                R.style.Codex_Widgets_EditTextTheme
        ).apply {
            body_edit_text.setText(getString(R.styleable.EditText_codex_text) ?: "")
            body_edit_text.hint = getString(R.styleable.EditText_codex_hint) ?: ""
            title_edit_text.text = getString(R.styleable.EditText_codex_title) ?: ""
            body_edit_text.inputType = when (getInt(R.styleable.EditText_codex_inputType, 0)) {
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else -> {
                    Log.w(EditText::class.java.simpleName, "Current input type not supported!")
                    InputType.TYPE_NULL
                }
            }
            body_edit_text.setTextColor(
                    getColor(
                            R.styleable.EditText_codex_textColor,
                            android.R.attr.editTextColor
                    )
            )
            title_edit_text.setTextColor(
                    getColor(
                            R.styleable.EditText_codex_titleColor,
                            android.R.attr.textColor
                    )
            )
            recycle()
        }
        body_edit_text.setOnFocusChangeListener { _, hasFocus ->
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

    var text: String by editTextDelegate(body_edit_text)

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            text = body_edit_text.text.toString()
            id = getId()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            if (id == state.id) {
                body_edit_text.setText(state.text)
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }


    class SavedState : BaseSavedState {
        var text: String = ""
        var id: Int = -1

        constructor(source: Parcel?) : super(source) {
            text = source?.readString() ?: ""
            id = source?.readInt() ?: -1
        }

        constructor(parcelable: Parcelable?) : super(parcelable)

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
            out?.writeInt(id)
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

fun editTextDelegate(editText: android.widget.EditText): ReadWriteProperty<EditText, String> {
    return object : ReadWriteProperty<EditText, String> {
        override fun getValue(thisRef: EditText, property: KProperty<*>): String {
            return editText.text.toString()
        }

        override fun setValue(thisRef: EditText, property: KProperty<*>, value: String) {
            editText.setText(value)
        }

    }
}
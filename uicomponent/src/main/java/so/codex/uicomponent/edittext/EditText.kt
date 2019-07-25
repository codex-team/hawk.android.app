package so.codex.uicomponent.edittext

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_edit_text.view.*
import so.codex.uicomponent.R

class EditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.view_edit_text, this)

        context.obtainStyledAttributes(attrs, R.styleable.EditText).apply {
            body_edit_text.setText(getString(R.styleable.EditText_text) ?: "")
            body_edit_text.hint = getString(R.styleable.EditText_hint) ?: ""
            title_edit_text.text = getString(R.styleable.EditText_title) ?: ""
            body_edit_text.inputType = when(getInt(R.styleable.EditText_inputType, 0)){
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                else -> {
                    Log.w(EditText::class.java.simpleName, "Current input type not supported!")
                    InputType.TYPE_NULL
                }
            }
            body_edit_text.setTextColor(getColor(R.styleable.EditText_textColor, android.R.attr.editTextColor))
            title_edit_text.setTextColor(getColor(R.styleable.EditText_titleColor, android.R.attr.textColor))
//            card_background.background = getDrawable
            //val stateListDrawable = card_background.drawable!!
            //if(stateListDrawable is StateListDrawable){
            /*ContextCompat.getDrawable(context ,R.drawable.edit_text_background)!!.let {
                if(it is GradientDrawable){

                }
            }*/
            recycle()
        }
        body_edit_text.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                card_background.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_text_background_selected))
            } else{
                card_background.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_text_background))
            }
        }
    }
}
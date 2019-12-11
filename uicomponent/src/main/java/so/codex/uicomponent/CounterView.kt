package so.codex.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.customSearchViewStyle
) : TextView(context, attrs, defStyleAttr) {

    companion object {
        private val STATE_READED = arrayOf(R.attr.)
    }

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CounterView,
            defStyleAttr,
            R.style.Codex_Widgets_CounterView
        ).apply {

        }.recycle()
    }
}
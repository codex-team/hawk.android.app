package so.codex.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

class ErrorValueIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private val maxValue = 9_999

    init {
        maxWidth = paint.measureText("+${maxValue.toFormattedString()}").toInt()
    }

    private fun Int.toFormattedString(): String = if (this < 1_000)
        this.toString()
    else
        this.toString().toCharArray().reversed().foldRightIndexed("") { indx, ch, acc ->
            ch + if (indx == 3) {
                " $acc"
            } else {
                acc
            }
        }

    override fun onDraw(canvas: Canvas?) {
        //canvas?.drawRoundRect(0f, 0f, width, height, )
        super.onDraw(canvas)
    }


}
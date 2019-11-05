package so.codex.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class ErrorValueIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private val maxValue = 9_999
    private var rectF = RectF()

    init {
        maxWidth = paint.measureText("+${maxValue.toFormattedString()}").toInt()
        textAlignment = View.TEXT_ALIGNMENT_CENTER
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(rectF, 3.toDp(), 3.toDp(), paint)
        super.onDraw(canvas)
    }

    fun Int.toDp() = this * context.resources.displayMetrics.density


}
package so.codex.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toRectF
import kotlin.properties.Delegates

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.customSearchViewStyle
) : TextView(context, attrs, defStyleAttr) {

    companion object {
        private val STATE_READ = intArrayOf(R.attr.state_read)
    }

    var isRead = false
    var readColor = Color.GRAY
    var unreadColor = Color.RED
    private var backgroundRect = Rect()
    private val backgroundPaint = Paint()


    var count by Delegates.observable(0) { _, old, new ->
        if(new < 0) {
            throw IllegalArgumentException("Count not be negative!")
        }
        text = if (new >= 1e5) {
            "9 999"
        } else {
            val buf = new.toString()
            if (buf.length == 4) {
                "${new / 1000} ${new % 1000}"
            } else {
                buf
            }
        }
    }

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CounterView,
            defStyleAttr,
            R.style.Codex_Widgets_CounterView
        ).apply {
            isRead = getBoolean(R.styleable.CounterView_state_read, false)
            readColor = getColor(R.styleable.CounterView_read_color, Color.GRAY)
            unreadColor = getColor(R.styleable.CounterView_unread_color, Color.RED)
            count = getInt(R.styleable.CounterView_count, 0)
            recycle()
        }
        backgroundPaint.color = if(isRead) readColor else unreadColor
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace)
        if (isRead) {
            View.mergeDrawableStates(drawableState, STATE_READ)
        }
        return drawableState
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backgroundRect.set(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(backgroundRect.toRectF(), 4f, 4f, backgroundPaint)
        super.onDraw(canvas)
    }
}
package so.codex.uicomponent

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView

class BorderedImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private val mRect: RectF by lazy {
        RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    private val corners by lazy {
        5f * resources.displayMetrics.density
    }
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
    }

    private val mPath = Path()

    private val mPaintCircle = Paint().apply {
        color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        mPath.rewind()
        mPath.addRoundRect(mRect, corners, corners, Path.Direction.CCW)
        canvas?.clipPath(mPath)
        super.onDraw(canvas)

    }
}
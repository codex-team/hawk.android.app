package so.codex.uicomponent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Simple image view with rounded corners
 * @author Shiplayer
 */
class BorderedImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.borderedImageViewStyle
) : ImageView(context, attrs, defStyleAttr) {
    /**
     * Radius of rounded corners
     */
    private var mCorners = 0f
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        context.obtainStyledAttributes(
            attrs,
            R.styleable.BorderedImageView,
            defStyleAttr,
            R.style.Codex_Widgets_BorderedImageViewStyle
        ).apply {

            mCorners = getDimension(R.styleable.BorderedImageView_corners, 0f)

            recycle()
        }
    }

    /**
     * Brush that painted of mask for image view
     */
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = this@BorderedImageView.xfermode
    }

    /**
     * lazy initializing of mask
     */
    private val mask: Bitmap by lazy {
        val mask = Bitmap.createBitmap(
            width - paddingRight - paddingLeft,
            height - paddingBottom - paddingTop,
            Bitmap.Config.ARGB_8888
        )
        Canvas(mask).apply {
            val rect = RectF(0f, 0f, mask.width.toFloat(), mask.height.toFloat())
            val paint = Paint()
            paint.isAntiAlias = true
            paint.xfermode = null
            paint.color = Color.BLACK
            drawRoundRect(rect, mCorners, mCorners, paint)
            paint.xfermode = xfermode
            paint.color = Color.WHITE
            drawRect(rect, paint)
            paint.xfermode = null
        }
        mask
    }

    private val density by lazy {
        resources.displayMetrics.density
    }

    /**
     * Not supported yet
     */
    public fun setCorners(dp: Int) {
        mCorners = dp * density

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(mask, 0f, 0f, mPaint)
    }
}
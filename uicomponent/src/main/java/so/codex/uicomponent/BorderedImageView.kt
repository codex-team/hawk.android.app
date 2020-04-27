package so.codex.uicomponent

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * Simple image view with rounded corners
 * @author Shiplayer
 */
class BorderedImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.borderedImageViewStyle
) : AppCompatImageView(context, attrs, defStyleAttr) {
    /**
     * Radius of rounded corners
     */
    private var mCorners = 0f

    /**
     * Screen density of phone
     */
    private val density by lazy {
        resources.displayMetrics.density
    }

    init {
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
     * Calculate width and height for view. View should me only square.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth = min(heightSize, widthSize)
        val desiredHeight = desiredWidth

        var width = 0
        var height = 0

        when (widthMode) {
            MeasureSpec.AT_MOST -> {
                width = widthSize
            }
            MeasureSpec.EXACTLY,
            MeasureSpec.UNSPECIFIED -> {
                width = desiredWidth
            }
        }

        when (heightMode) {
            MeasureSpec.AT_MOST -> {
                height = heightSize
            }
            MeasureSpec.EXACTLY,
            MeasureSpec.UNSPECIFIED -> {
                height = desiredHeight
            }
        }
        width = min(width, height)
        height = min(width, height)
        setMeasuredDimension(width, height)
    }

    /**
     * After calculate width and height, we can get outline provider and calculate rect for it.
     */
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        if (mCorners != 0f) {
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(
                        view.paddingLeft,
                        view.paddingTop,
                        view.width - view.paddingRight,
                        view.height - view.paddingBottom,
                        mCorners
                    )
                }
            }
            clipToOutline = true
        }
    }

    /**
     * Not supported yet
     */
    public fun setCorners(dp: Int) {
        mCorners = dp * density

        invalidate()
    }
}
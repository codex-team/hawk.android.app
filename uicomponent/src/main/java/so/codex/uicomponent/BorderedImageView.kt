package so.codex.uicomponent

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView

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

        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    view.left + view.paddingLeft,
                    view.top + view.paddingTop,
                    view.right - view.paddingRight,
                    view.bottom - view.paddingBottom,
                    mCorners
                )
            }
        }
        clipToOutline = true
    }

    /**
     * Not supported yet
     */
    public fun setCorners(dp: Int) {
        mCorners = dp * density

        invalidate()
    }
}
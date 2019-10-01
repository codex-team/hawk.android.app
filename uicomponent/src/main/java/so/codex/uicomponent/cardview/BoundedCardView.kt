package so.codex.uicomponent.cardview

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import so.codex.uicomponent.R

/**
 * This is like as CardView but have some limits like as max height and max width
 * @author Shiplayer
 */
class BoundedCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    /**
     * max width of card view
     */
    private val mMaxWidth: Int
    /**
     * max height of card view
     */
    private val mMaxHeight: Int

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BoundedCardView).apply {
            mMaxWidth = getDimensionPixelSize(R.styleable.BoundedCardView_maxWidth, 0)
            mMaxHeight = getDimensionPixelSize(R.styleable.BoundedCardView_maxHeight, 0)

            recycle()
        }
    }

    /**
     * Calculate size of card view
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val newWidth = if (mMaxWidth in 1 until measuredWidth) {
            val measureMode = MeasureSpec.getMode(widthMeasureSpec)
            MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode)
        } else
            widthMeasureSpec

        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val newHeight = if (mMaxHeight in 1 until measuredHeight) {
            val measureMode = MeasureSpec.getMode(heightMeasureSpec)
            MeasureSpec.makeMeasureSpec(mMaxHeight, measureMode)
        } else
            heightMeasureSpec
        super.onMeasure(newWidth, newHeight)
    }
}
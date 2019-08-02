package so.codex.uicomponent.cardview

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import so.codex.uicomponent.R


class BoundedCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val mMaxWidth: Int
    private val mMaxHeight: Int

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BoundedCardView).apply {
            mMaxWidth = getDimensionPixelSize(R.styleable.BoundedCardView_maxWidth, 0)
            mMaxHeight = getDimensionPixelSize(R.styleable.BoundedCardView_maxHeight, 0)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val newWidth = if (mMaxWidth in 1 until measuredWidth) {
            val measureMode = MeasureSpec.getMode(widthMeasureSpec)
            MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode)
        } else
            widthMeasureSpec
        // Adjust height as necessary
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val newHeight = if (mMaxHeight in 1 until measuredHeight) {
            val measureMode = MeasureSpec.getMode(heightMeasureSpec)
            MeasureSpec.makeMeasureSpec(mMaxHeight, measureMode)
        } else
            heightMeasureSpec
        super.onMeasure(newWidth, newHeight)
    }
}
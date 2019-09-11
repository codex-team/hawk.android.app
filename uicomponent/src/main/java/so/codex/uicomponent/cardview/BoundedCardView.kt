package so.codex.uicomponent.cardview

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import so.codex.uicomponent.R

/**
 * Является обычной CardView, но имеет некоторые ограниченич, такие как максимальная высота и
 * максимальная ширина.
 * @author Shiplayer
 */
class BoundedCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    /**
     * Максимальная ширина, которую карточка может занять
     */
    private val mMaxWidth: Int
    /**
     * Максимальная высота, которую карточка может занять
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
     * Вычисляем необходимые размеры для нашей карточки
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
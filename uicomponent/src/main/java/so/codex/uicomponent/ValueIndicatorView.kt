package so.codex.uicomponent

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView


class ValueIndicatorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    companion object {
        private val STATE_MORE = intArrayOf(R.attr.is_more)
        private val STATE_LESS = intArrayOf(R.attr.is_less)
        private val STATE_DEFAULT = intArrayOf()
    }

    private var colorStateList: ColorStateList? = null

    var baseCount: Int = 0

    var count: Int = 0
        set(value) {
            if (field != value) {
                field = value
                when {
                    field > baseCount -> onMore()
                    field < baseCount -> onLess()
                    else -> onEquals()
                }
            }
        }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val count = text.toString().toIntOrNull()
        if (count != null) {
            super.setText(text, type)
            this.count = count
        }
    }

    var isMore = false
        private set(value) {
            Log.i("ValueIndicatorView", "isMore.field = $field; isMore.value = $value")
            if (field != value) {
                field = value
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && colorStateList != null) {
                if (value) {
                    setTextViewDrawableColor(STATE_MORE)
                } else if (!isLess) {
                    setTextViewDrawableColor(STATE_DEFAULT)
                }
            }
        }

    var isLess = false
        private set(value) {
            Log.i("ValueIndicatorView", "isLess.field = $field; isLess.value = $value")
            if (field != value) {
                field = value
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && colorStateList != null) {
                if (value) {
                    setTextViewDrawableColor(STATE_LESS)
                } else if (!isMore) {
                    setTextViewDrawableColor(STATE_DEFAULT)
                }
            }
        }

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.ValueIndicatorView,
                defStyleAttr,
                0
        ).apply {

            colorStateList = getColorStateList(R.styleable.ValueIndicatorView_viewDrawableTint)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (colorStateList != null)
                    compoundDrawableTintList = colorStateList
            }

            isMore = getBoolean(R.styleable.ValueIndicatorView_is_more, false)
            isLess = getBoolean(R.styleable.ValueIndicatorView_is_less, false)

            baseCount = getInt(R.styleable.ValueIndicatorView_baseCount, 0)

            count = getInt(R.styleable.ValueIndicatorView_count, 0)

            recycle()
        }
    }

    private fun onMore() {
        isMore = true
        isLess = false
        refreshDrawableState()
    }

    private fun onLess() {
        isMore = false
        isLess = true
        refreshDrawableState()
    }

    private fun onEquals() {
        isMore = false
        isLess = false
        refreshDrawableState()
    }


    private fun setTextViewDrawableColor(state: IntArray) {
        if (colorStateList != null) {
            for (drawable in compoundDrawables.filterNotNull()) {
                val color = colorStateList!!.getColorForState(state, colorStateList!!.defaultColor)
                drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 2)
        if (isMore)
            View.mergeDrawableStates(drawableState, STATE_MORE)
        if (isLess)
            View.mergeDrawableStates(drawableState, STATE_LESS)
        return drawableState
    }
}
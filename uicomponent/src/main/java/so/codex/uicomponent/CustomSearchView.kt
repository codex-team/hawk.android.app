package so.codex.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.view_search.view.ic_clear
import kotlinx.android.synthetic.main.view_search.view.ic_search
import kotlinx.android.synthetic.main.view_search.view.search_edit_text
import kotlinx.android.synthetic.main.view_search.view.transit_container

/**
 * Implementation composition of view for searching and using animation
 */
class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.customSearchViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * Radius of corners for rounded form
     */
    private var corners: Float
    /**
     * Brush for painting background
     */
    private val paint = Paint()
    /**
     * Size of form
     */
    private val rectF = RectF()
    /**
     * In while changing of text to invoke [listener]
     */
    private var listener: SearchListener? = null
    /**
     * Save references of animation. If view destroyed then free memory
     */
    private var animation: ViewPropertyAnimator? = null
    /**
     * FOR DEBUG
     */
    private var count = 0

    /**
     * View for changing of text for searching
     */
    private var searchText: EditText

    /**
     * Initialize view
     */
    init {
        val view = View.inflate(context, R.layout.view_search, this)

        searchText = view.search_edit_text

        setWillNotDraw(false)
        //corners = dpToPx(5.0f)
        rectF.apply {
            left = 0f
            top = 0f
        }
        paint.isAntiAlias = true

        context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomSearchView,
            defStyleAttr,
            R.style.Codex_Widgets_CustomSearchViewTheme
        ).apply {
            paint.color = getColor(
                R.styleable.CustomSearchView_cardBackgroundColor,
                Color.TRANSPARENT
            )
            corners = getDimension(R.styleable.CustomSearchView_corners, 0f)
            ic_search.setColorFilter(
                getColor(R.styleable.CustomSearchView_imageTint, Color.BLACK),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ic_clear.setColorFilter(
                getColor(R.styleable.CustomSearchView_imageTint, Color.BLACK),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            /*searchText.setTextColor(
                    getColor(
                            R.styleable.CustomSearchView_textColor,
                            Color.BLACK
                    )
            )*/
            searchText.hint = getString(R.styleable.CustomSearchView_hint)
            recycle()
        }
        ic_clear.isClickable = true
        initListeners()
        setOnClickListener { requestFocusEditText() }
        ic_search.setOnClickListener {
            requestFocusEditText()
        }
    }

    /**
     * Request focus on edit text view for typing text
     */
    private fun requestFocusEditText() {
        if (!searchText.isFocused) {
            searchText.requestFocus()
            searchText.postDelayed(
                {
                    val keyboard = getSystemService(
                        context,
                        InputMethodManager::class.java
                    )
                    keyboard!!.showSoftInput(searchText, 0)
                },
                100
            )
        }
    }

    /**
     * Initialize all listeners that need for that view
     */
    private fun initListeners() {
        searchText.addTextChangedListener(object : TextWatcher {
            /**
             * Not used
             */
            override fun afterTextChanged(s: Editable?) {

            }

            /**
             * Not used
             */
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            /**
             * Showing button for clear all text and send that text to [listener]
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    ic_clear.visibility = View.GONE
                } else {
                    ic_clear.visibility = View.VISIBLE
                }
                listener?.search(s.toString())
            }
        })

        searchText.setOnFocusChangeListener { v, hasFocus ->
            count++
            Log.i("SearchView", "${v::class.java.simpleName} $hasFocus $count")
            transit_container.postDelayed(
                {
                    TransitionManager.beginDelayedTransition(
                        transit_container
                    )
                    if (hasFocus) {
                        ic_search.visibility = View.GONE
                        searchText.setPadding(
                            dpToPx(7f).toInt(),
                            paddingTop,
                            paddingEnd,
                            paddingBottom
                        )
                    } else {
                        ic_search.visibility = View.VISIBLE
                        searchText.setPadding(
                            dpToPx(4f).toInt(),
                            paddingTop,
                            paddingEnd,
                            paddingBottom
                        )
                    }
                },
                300
            )
            //TransitionManager.beginDelayedTransition(transit_container)
        }
        ic_clear.setOnClickListener {
            searchText.setText("")
        }
    }

    /**
     * Calculating measure of view
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))
        Log.v("Chart onMeasure w", MeasureSpec.getSize(widthMeasureSpec).toString())
        Log.v("Chart onMeasure h", MeasureSpec.getSize(heightMeasureSpec).toString())
/*        rectF.apply {
            right = MeasureSpec.getSize(widthMeasureSpec).toFloat()
            bottom = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        }*/
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Method for set up corners for rectangle
     * @param dp radius for corners in dp dimensions
     */
    fun setCorners(dp: Float) {
        corners = dpToPx(dp)
        invalidate()
    }

    /**
     * Method for set up color of background view
     * @param color Need pass already getting color from resources
     */
    fun setCardBackgroundColor(@ColorInt color: Int) {
        paint.color = color
        invalidate()
    }

    /**
     * Set color filter for image
     */
    fun setImageColorFilter(@ColorInt color: Int) {
        ic_search.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    /**
     * Set search listener [SearchListener]
     */
    fun setSearchListener(listener: SearchListener) {
        this.listener = listener
    }

    /**
     * Cancel animation if it running
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animation?.cancel()
    }

    /**
     * Convert from dp to px
     */
    private fun dpToPx(dp: Float): Float = dp * resources.displayMetrics.density

    /**
     * Update [rectF] if size of view was changed.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.apply {
            right = w.toFloat()
            bottom = h.toFloat()
        }
    }

    /**
     * Draw rounded rectangle
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("onDraw", "width = $width; height = $height")
        canvas?.drawRoundRect(rectF, corners, corners, paint)
    }

    /**
     * Interface that invoke method searching if user typed something in field
     */
    interface SearchListener {
        /**
         * Method invoke after getting text from view and send to search information
         * @param text Text for searching something
         */
        fun search(text: String)
    }

    /**
     * Save state fields of view in Parcelable.
     */
    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            text = searchText.text.toString()
            id = getId()
            requestFocus = searchText.isFocused
            if (searchText.isFocused)
                position = searchText.selectionStart
        }
    }

    /**
     * Restore state fields of view and fill it from Parcelable
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            if (id == state.id) {
                searchText.post {
                    searchText.setText(state.text)
                    if (state.requestFocus) {
                        searchText.requestFocus()
                        searchText.setSelection(state.position)
                    }
                }
            }
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    /**
     * Class that contain information about fields of current view
     */
    class SavedState : BaseSavedState {
        /**
         * Searched text
         */
        var text: String = ""
        /**
         * Id of current view
         */
        var id: Int = -1
        /**
         * View was focused
         */
        var requestFocus = false
        /**
         * Current position of cursor
         */
        var position = text.length

        /**
         * Constructor with reading data from parcel and fill fields
         */
        constructor(source: Parcel?) : super(source) {
            text = source?.readString() ?: ""
            id = source?.readInt() ?: -1
            requestFocus = (source?.readByte() ?: 0.toByte()) != 0.toByte()
            position = source?.readInt() ?: text.length
        }

        /**
         * That same that constructor above
         */
        constructor(parcelable: Parcelable?) : super(parcelable)

        /**
         * Save fields in parcel for saving state
         */
        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
            out?.writeInt(id)
            out?.writeByte(if (requestFocus) 1 else 0)
            out?.writeInt(position)
        }

        /**
         * describe contents
         */
        override fun describeContents(): Int {
            return 0
        }

        /**
         * Creator used for loading and saving fields
         */
        companion object CREATOR : Parcelable.Creator<SavedState> {
            /**
             * Create a new instance of the Parcelable class, instantiating it
             * from the given Parcel whose data had previously been written
             */
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            /**
             * Create a new array of the Parcelable class.
             */
            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
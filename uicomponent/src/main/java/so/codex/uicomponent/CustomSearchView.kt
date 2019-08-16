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
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.android.synthetic.main.view_search.view.*


class CustomSearchView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.customSearchViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    private var corners: Float
    private val paint = Paint()
    private val rectF = RectF()
    private var listener: SearchListener? = null
    private var animation: ViewPropertyAnimator? = null
    private var count = 0

    init {
        View.inflate(context, R.layout.view_search, this)
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
            /*search_edit_text.setTextColor(
                    getColor(
                            R.styleable.CustomSearchView_textColor,
                            Color.BLACK
                    )
            )*/
            search_edit_text.hint = getString(R.styleable.CustomSearchView_hint)
            recycle()
        }
        ic_clear.isClickable = true
        initListeners()
        setOnClickListener { requestFocusEditText() }
        ic_search.setOnClickListener {
            requestFocusEditText()
        }
    }

    private fun requestFocusEditText() {
        if (!search_edit_text.isFocused) {
            search_edit_text.requestFocus()
            search_edit_text.postDelayed({
                                             val keyboard = getSystemService(
                                                     context,
                                                     InputMethodManager::class.java
                                             )
                                             keyboard!!.showSoftInput(search_edit_text, 0)
                                         }, 200)
        }
    }

    private fun initListeners() {
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    ic_clear.visibility = View.GONE
                } else {
                    ic_clear.visibility = View.VISIBLE
                }
                listener?.search(s.toString())
            }
        })

        search_edit_text.setOnFocusChangeListener { v, hasFocus ->
            count++
            Log.i("SearchView", "${v::class.java.simpleName} $hasFocus $count")
            transit_container.postDelayed({
                                              TransitionManager.beginDelayedTransition(
                                                      transit_container
                                              )
                                              if (hasFocus) {
                                                  ic_search.visibility = View.GONE
                                              } else {
                                                  ic_search.visibility = View.VISIBLE
                                              }
                                          }, 100)
            //TransitionManager.beginDelayedTransition(transit_container)
        }
        ic_clear.setOnClickListener {
            search_edit_text.setText("")
        }
    }

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

    fun setImageColorFilter(@ColorInt color: Int) {
        ic_search.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
    }

    fun setSearchListener(listener: SearchListener) {
        this.listener = listener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animation?.cancel()
    }

    private fun dpToPx(dp: Float): Float = dp * resources.displayMetrics.density

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.apply {
            right = w.toFloat()
            bottom = h.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("onDraw", "width = $width; height = $height")
        canvas?.drawRoundRect(rectF, corners, corners, paint)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            text = search_edit_text.text.toString()
            id = getId()
            requestFocus = search_edit_text.isFocused
            if (search_edit_text.isFocused)
                position = search_edit_text.selectionStart
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            if (id == state.id) {
                search_edit_text.post {
                    search_edit_text.setText(state.text)
                    if (state.requestFocus) {
                        search_edit_text.requestFocus()
                        search_edit_text.setSelection(state.position)
                    }
                }
            }
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    interface SearchListener {
        fun search(text: String)
    }

    class SavedState : BaseSavedState {
        var text: String = ""
        var id: Int = -1
        var requestFocus = false
        var position = text.length

        constructor(source: Parcel?) : super(source) {
            text = source?.readString() ?: ""
            id = source?.readInt() ?: -1
            requestFocus = (source?.readByte() ?: 0.toByte()) != 0.toByte()
            position = source?.readInt() ?: text.length
        }

        constructor(parcelable: Parcelable?) : super(parcelable)

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeString(text)
            out?.writeInt(id)
            out?.writeByte(if (requestFocus) 1 else 0)
            out?.writeInt(position)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
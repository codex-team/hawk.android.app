package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.view_project_item.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate
import so.codex.utils.getColorById

/**
 * Composition of view for showing elements of Project in list
 * @author Shiplayer
 */
class ProjectItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.projectItemViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * Inflate view and attach
     */
    val view: View = View.inflate(context, R.layout.view_project_item, this)

    /**
     * Logo image
     */
    val logoImage: ImageView = view.logo

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ProjectItemView,
            defStyleAttr,
            R.style.Codex_Widgets_ProjectItemViewStyle
        ).apply {
            val drawable = getDrawable(R.styleable.ProjectItemView_src)
            if (drawable != null) {
                logoImage.setImageDrawable(drawable)
            }
            recycle()
        }
    }

    /**
     * Title name of item
     */
    var title by textViewDelegate(view.tv_title)
    /**
     * Message of item
     */
    var message by textViewDelegate(view.tv_message)
    /**
     * Count of errors
     */
    var count by textViewDelegate(view.tv_count) { v, text ->
        if (text.isEmpty() || text == "0") {
            v.visibility = View.GONE
        } else {
            v.visibility = View.VISIBLE
        }
    }
    /**
     * Uuid of project
     */
    var uuid: String = ""

    /**
     * Default bitmap if logo not set up
     */
    private var defaultImage: Bitmap? = null

    /**
     * Set logo image like as resource id
     */
    fun setLogoImageResource(resourceId: Int) {
        logoImage.setImageResource(resourceId)
    }

    /**
     * Generate default image for logo by name of project
     */
    fun setDefaultImage() {
        logoImage.post {
            if (defaultImage == null) {
                val firstChar = title.split(" ").fold("") { acc, s -> acc + s.first() }.toString()
                    .toUpperCase()
                defaultImage = Bitmap.createBitmap(
                    logoImage.width,
                    logoImage.height,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(defaultImage!!)
                val fontPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    textSize = 14f * resources.displayMetrics.density + 0.5f
                    typeface = Typeface.create("roboto", Typeface.BOLD)
                    color = view.tv_title.currentTextColor
                }
                val bounds = Rect()
                fontPaint.getTextBounds(firstChar, 0, firstChar.length, bounds)
                fontPaint.textAlign = Paint.Align.LEFT
                canvas.drawColor(getColorById(uuid))
                val centerX = logoImage.width / 2f - bounds.exactCenterX()
                val centerY = logoImage.height.toFloat() / 2f - bounds.exactCenterY()
                canvas.drawText(firstChar, centerX, centerY, fontPaint)
            }
            logoImage.setImageBitmap(defaultImage)
        }
    }


    /**
     * Save field state of current view
     */
    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()
    }

    /**
     * Restore field state of current view from [Parcelable]
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
    }

    // TODO Create class for saving field state
}
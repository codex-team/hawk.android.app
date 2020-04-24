package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.workspace_item.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate
import so.codex.utils.getColorById

class WorkspaceItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.projectItemViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    private val view = View.inflate(context, R.layout.workspace_item, this)
    val logoImage: ImageView = view.workspace_icon
    private var defaultImage: Bitmap? = null
    var title by textViewDelegate(view.workspace_name)
    var uuid: String = ""

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.WorkspaceItemView,
            defStyleAttr,
            R.style.Codex_Widgets_WorkspaceItemViewStyle
        ).apply {
            val drawable = getDrawable(R.styleable.WorkspaceItemView_src_ws)
            if (drawable != null) {
                logoImage.setImageDrawable(drawable)
            }
            recycle()
        }
    }

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
                    color = view.workspace_name.currentTextColor
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
}
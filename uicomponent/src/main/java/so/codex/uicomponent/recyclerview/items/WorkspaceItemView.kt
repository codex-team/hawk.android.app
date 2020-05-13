package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.workspace_item.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate
import so.codex.utils.getColorById

/**
 * Composition of view for showing elements of Workspace in list
 * @author YorkIsMine
 */
class WorkspaceItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.projectItemViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * Inflate view and attach
     */
    private val view = View.inflate(context, R.layout.workspace_item, this)

    /**
     * Logo image
     */
    val logoImage: ImageView = view.workspace_icon

    /**
     * Default bitmap if logo not set up
     */
    private var defaultImage: Bitmap? = null

    /**
     * Title name of item
     */
    var title by textViewDelegate(view.workspace_name)

    var isViewSelected: Boolean = false
        set(value) {
            field = value
            if (value)
                view.container_workspace.background =
                    ContextCompat.getDrawable(context, R.drawable.clicked_back)
            else
                view.container_workspace.background =
                    ContextCompat.getDrawable(context, R.drawable.disabled)
        }

    /**
     * Uuid of project
     */
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

    /**
     * Set default image if response hasn't got image
     */
    fun setDefaultImage() {
        logoImage.post {
            Log.d("WorkspaceItemView", "defaultImage is null for $title? ${defaultImage == null}")
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
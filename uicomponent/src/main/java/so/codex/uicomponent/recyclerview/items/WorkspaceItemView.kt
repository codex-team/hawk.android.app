package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.workspace_item.view.container_workspace
import kotlinx.android.synthetic.main.workspace_item.view.workspace_icon
import kotlinx.android.synthetic.main.workspace_item.view.workspace_name
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate

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
     * Title name of item
     */
    var title by textViewDelegate(view.workspace_name)

    /**
     * Checks if [WorkspaceItemView] was tapped or not
     */
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
}
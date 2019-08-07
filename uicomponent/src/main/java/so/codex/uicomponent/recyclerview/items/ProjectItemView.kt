package so.codex.uicomponent.recyclerview.items

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_project_item.view.*
import so.codex.uicomponent.R
import so.codex.uicomponent.textViewDelegate

class ProjectItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.projectItemViewStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    val view: View = View.inflate(context, R.layout.view_project_item, this)

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.ProjectItemView,
                defStyleAttr,
                R.style.Codex_Widgets_ProjectItemViewStyle
        ).apply {
            val drawable = getDrawable(R.styleable.ProjectItemView_src)
            if (drawable != null) {
                view.logo.setImageDrawable(drawable)
            }
            recycle()
        }
    }

    val title by textViewDelegate(view.tv_title)
    val message by textViewDelegate(view.tv_message)
    val count by textViewDelegate(view.tv_count)

    fun setLogoImageResource(resourceId: Int) {
        view.logo.setImageResource(resourceId)
    }


    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
    }
}
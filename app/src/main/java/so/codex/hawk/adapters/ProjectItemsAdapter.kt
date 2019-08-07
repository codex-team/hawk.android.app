package so.codex.hawk.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.ProjectItemView

class ProjectItemsAdapter : RecyclerView.Adapter<ProjectItemsAdapter.ProjectItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectItemViewHolder {
        val view = ProjectItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ProjectItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ProjectItemViewHolder, position: Int) {
        holder.bind()
    }

    class ProjectItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            if (itemView is ProjectItemView) {
                itemView.setLogoImageResource(R.drawable.ic_launcher_background)
            }
        }
    }
}
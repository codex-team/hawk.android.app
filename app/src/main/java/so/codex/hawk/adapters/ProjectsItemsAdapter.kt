package so.codex.hawk.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.entity.Project
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.ProjectItemView
import kotlin.properties.Delegates

class ProjectsItemsAdapter(val itemClick: (Project) -> Unit) :
    RecyclerView.Adapter<ProjectsItemsAdapter.ProjectItemViewHolder>() {

    var data: List<Project> by Delegates.observable(listOf()) { _, old, new ->
        notify(old, new) { left, right ->
            left == right
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectItemViewHolder {
        val view = ProjectItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ProjectItemViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProjectItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            itemClick(data[position])
        }
    }

    class ProjectItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(project: Project) {
            if (itemView is ProjectItemView) {
                itemView.uuid = project.id
                if (project.image.isNotEmpty())
                    Picasso.get()
                            .load(project.image)
                            .error(R.drawable.ic_error_outline_black_24dp)
                            .into(itemView.logoImage)
                else
                    itemView.setDefaultImage()
                itemView.title = project.name
                if (project.events.isNotEmpty()) {
                    itemView.message = project.events.first().payload.title
                }
                itemView.count = project.events.size.toString()
            }
        }
    }
}
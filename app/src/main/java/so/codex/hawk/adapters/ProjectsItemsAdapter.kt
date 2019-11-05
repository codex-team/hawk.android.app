package so.codex.hawk.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.entity.Project
import so.codex.codexbl.main.ImageProvider
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.ProjectItemView
import so.codex.utils.getColorById
import kotlin.properties.Delegates

/**
 * UI element for showing projects
 */
class ProjectsItemsAdapter(val itemClick: (Project) -> Unit) :
    RecyclerView.Adapter<ProjectsItemsAdapter.ProjectItemViewHolder>() {

    /**
     * List of project that showed in RecyclerView. Used delegate for change old elements in the list on new elements
     * and update element on this position.
     */
    var data: List<Project> by Delegates.observable(listOf()) { _, old, new ->
        notify(old, new) { left, right ->
            left == right
        }
    }

    /**
     * Create view from module "uicomponent" for project item view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectItemViewHolder {
        val view = ProjectItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ProjectItemViewHolder(view)
    }

    /**
     * Get size of projects elements in data
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Bind data with holder by position
     */
    override fun onBindViewHolder(holder: ProjectItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            itemClick(data[position])
        }
    }

    /**
     * Class that representation of project in recycler view
     */
    class ProjectItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Data binding [project] with UI elements
         * @param project Project that showed in UI
         */
        fun bind(project: Project) {
            if (itemView is ProjectItemView) {
                itemView.uuid = project.id
                if (project.image.isNotEmpty())
                    Picasso.get()
                        .load(project.image)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(itemView.logoImage)
                else
                    itemView.setDefaultImage(
                        ImageProvider.instance.getImageByUuid(
                            itemView.context,
                            project.id,
                            project.name,
                            getColorById(project.id)
                        )
                    )
                itemView.title = project.name
                if (project.events.isNotEmpty()) {
                    itemView.message = project.events.first().payload.title
                }
                itemView.count = project.events.size.toString()
            }
        }
    }
}
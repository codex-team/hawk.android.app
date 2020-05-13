package so.codex.hawk.adapters

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.entity.Workspace
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.WorkspaceItemView

//ToDo add docs
class WorkspaceItemAdapter : RecyclerView.Adapter<WorkspaceItemAdapter.WorkspaceItemHolder>() {
    inner class WorkspaceItemHolder(
        private val view: WorkspaceItemView
    ) : RecyclerView.ViewHolder(view) {
        fun bind(workspace: Workspace) {
            if (!workspace.hasId()) bindAddWorkspaceBtn()
            else bindWorkspace(workspace)
        }

        private fun bindAddWorkspaceBtn() {
            view.logoImage.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    R.drawable.add_workspace
                )
            )
            view.setOnClickListener(null)
            view.title = "Add workspace"
        }

        private fun bindWorkspace(workspace: Workspace) {
            view.uuid = workspace.id
            view.title = workspace.name
            if (workspace.hasImage()) {
                Picasso.get()
                    .load(workspace.image)
                    .error(R.drawable.ic_error_outline_black_24dp)
                    .into(view.logoImage)
            } else {
                view.logoImage.setImageBitmap(
                    DefaultImageLoader.get(
                        workspace.id,
                        workspace.name
                    ).loadImage()
                )
            }
            view.setOnClickListener {
                v?.disabled()
                view.clicked()
                v = view
            }
        }

    }

    private var v: WorkspaceItemView? = null


    private val workspaces: MutableList<Workspace> = mutableListOf()

    fun setData(data: List<Workspace>) {
        workspaces.addAll(data)
        notifyDataSetChanged()
    }

    fun setLastElem(lastWorkspace: Workspace) {
        workspaces.add(workspaces.size, lastWorkspace)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceItemHolder {
        val view =
            WorkspaceItemView(parent.context)

        return WorkspaceItemHolder(view)
    }

    override fun getItemCount(): Int = workspaces.size

    override fun onBindViewHolder(holder: WorkspaceItemHolder, position: Int) {
        holder.bind(workspaces[position])
    }
}
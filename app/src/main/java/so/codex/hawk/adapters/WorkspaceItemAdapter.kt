package so.codex.hawk.adapters

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.presenter.GaragePresenter.WorkspaceSelectedCallback
import so.codex.codexbl.view.IGarageView.WorkspaceViewModel
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.uicomponent.recyclerview.items.WorkspaceItemView

//ToDo add docs
class WorkspaceItemAdapter(val listener: WorkspaceSelectedCallback) :
    RecyclerView.Adapter<WorkspaceItemAdapter.WorkspaceItemHolder>() {
    inner class WorkspaceItemHolder(
        private val listener: WorkspaceSelectedCallback,
        private val view: WorkspaceItemView
    ) : RecyclerView.ViewHolder(view) {
        fun bind(workspace: WorkspaceViewModel) {
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

        private fun bindWorkspace(workspace: WorkspaceViewModel) {
            view.uuid = workspace.id
            view.title = workspace.name
            view.isViewSelected = workspace.isSelected
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
                view.isViewSelected = !view.isViewSelected
                if (view.isViewSelected) {
                    listener.select(workspace)
                }
            }
        }

    }

    private var v: WorkspaceItemView? = null


    var workspaces: List<WorkspaceViewModel> = listOf()

    /*fun setLastElem(lastWorkspace: Workspace) {
        workspaces.add(workspaces.size, lastWorkspace)
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceItemHolder {
        val view =
            WorkspaceItemView(parent.context)

        return WorkspaceItemHolder(listener, view)
    }

    override fun getItemCount(): Int = workspaces.size

    override fun onBindViewHolder(holder: WorkspaceItemHolder, position: Int) {
        holder.bind(workspaces[position])
    }
}
package so.codex.hawk.adapters

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import so.codex.codexbl.presenter.GaragePresenter.WorkspaceSelectedCallback
import so.codex.codexbl.view.IGarageView.WorkspaceViewModel
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.hawk.WorkspaceViewModelDiffUtil
import so.codex.uicomponent.recyclerview.items.WorkspaceItemView

/**
 * UI element for showing workspaces in drawer
 * @param listener needed for selecting workspace
 */
class WorkspaceItemAdapter(private val listener: WorkspaceSelectedCallback) :
    RecyclerView.Adapter<WorkspaceItemAdapter.WorkspaceItemHolder>() {

    /**
     * Class for holding given workspace
     * @see WorkspaceItemView
     */
    inner class WorkspaceItemHolder(
        private val listener: WorkspaceSelectedCallback,
        private val view: WorkspaceItemView
    ) : RecyclerView.ViewHolder(view) {

        /**
         * Bind an instance of workspace
         * @see [WorkspaceViewModel]
         * @param workspace given workspace
         */
        fun bind(workspace: WorkspaceViewModel) {
            if (!workspace.hasId()) bindAddWorkspaceBtn()
            else bindWorkspace(workspace)
        }

        /**
         * bind if the given workspace is Buttom
         * for adding new workspaces
         */
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

        /**
         * Bind if the given workspace is not Button
         * @param workspace for binding
         */
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

    /**
     * An instance of [WorkspaceViewModelDiffUtil]
     * need for adapter of workspaces
     * @see WorkspaceViewModelDiffUtil
     * @see WorkspaceItemAdapter
     */
    private val diffUtil = WorkspaceViewModelDiffUtil()

    /**
     * Set current list of workspaces
     * @param data current list of workspaces
     * for setting value of adapter
     */
    fun setData(data: List<WorkspaceViewModel>) {
        diffUtil.update(workspaces, data)
        workspaces = data
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
    }

    /**
     * Show "Add workspace button"
     */
    //TODO In MVP don't use!
    fun setLastElem(workspace: WorkspaceViewModel) {
//        workspaces.add(workspaces.size, workspace)
//        notifyDataSetChanged()
    }

    /**
     * List of workspaces
     */
    private var workspaces: List<WorkspaceViewModel> = listOf()

    /**
     * Create view from module "uicomponent" for workspace item view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceItemHolder {
        val view =
            WorkspaceItemView(parent.context)

        return WorkspaceItemHolder(listener, view)
    }

    /**
     * Get size of workspace elements in data
     */
    override fun getItemCount(): Int = workspaces.size

    /**
     * Bind data with holder by position
     */
    override fun onBindViewHolder(holder: WorkspaceItemHolder, position: Int) {
        holder.bind(workspaces[position])
    }
}
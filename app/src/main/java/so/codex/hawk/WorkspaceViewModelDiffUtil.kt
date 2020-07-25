package so.codex.hawk

import androidx.recyclerview.widget.DiffUtil
import so.codex.codexbl.view.IGarageView.WorkspaceViewModel

/**
 * Class for comparing items in list. Need for Drawer
 * when user tap on workspace in it
 * @see DiffUtil.Callback
 */
class WorkspaceViewModelDiffUtil : DiffUtil.Callback() {
    /**
     * Old workspace list
     */
    private var oldWorkspaceViewModelList: List<WorkspaceViewModel> = listOf()

    /**
     * New workspace list
     */
    private var newWorkspaceViewModelList: List<WorkspaceViewModel> = listOf()

    /**
     * Update current [oldWorkspaceViewModelList] and [newWorkspaceViewModelList]
     */
    fun update(
        oldWorkspaceViewModelList: List<WorkspaceViewModel>,
        newWorkspaceViewModelList: List<WorkspaceViewModel>
    ) {
        this.oldWorkspaceViewModelList = oldWorkspaceViewModelList
        this.newWorkspaceViewModelList = newWorkspaceViewModelList
    }

    /**
     * Checks if workspaces are same
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWorkspaceViewModelList[oldItemPosition] === newWorkspaceViewModelList[newItemPosition]
    }

    /**
     * @return size of old workspaceList
     */
    override fun getOldListSize(): Int = oldWorkspaceViewModelList.size

    /**
     * @return size of new workspaceList
     */
    override fun getNewListSize(): Int = newWorkspaceViewModelList.size

    /**
     * Checks if content of workspaces is same
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWorkspaceViewModelList[oldItemPosition] == newWorkspaceViewModelList[newItemPosition]
    }
}
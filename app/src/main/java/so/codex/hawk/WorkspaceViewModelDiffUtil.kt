package so.codex.hawk

import androidx.recyclerview.widget.DiffUtil
import so.codex.codexbl.view.IGarageView.WorkspaceViewModel

class WorkspaceViewModelDiffUtil : DiffUtil.Callback() {
    private var oldWorkspaceViewModelList: List<WorkspaceViewModel> = listOf()
    private var newWorkspaceViewModelList: List<WorkspaceViewModel> = listOf()

    fun update(
        oldWorkspaceViewModelList: List<WorkspaceViewModel>,
        newWorkspaceViewModelList: List<WorkspaceViewModel>
    ) {
        this.oldWorkspaceViewModelList = oldWorkspaceViewModelList
        this.newWorkspaceViewModelList = newWorkspaceViewModelList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWorkspaceViewModelList[oldItemPosition] === newWorkspaceViewModelList[newItemPosition]
    }

    override fun getOldListSize(): Int = oldWorkspaceViewModelList.size

    override fun getNewListSize(): Int = newWorkspaceViewModelList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldWorkspaceViewModelList[oldItemPosition] == newWorkspaceViewModelList[newItemPosition]
    }
}
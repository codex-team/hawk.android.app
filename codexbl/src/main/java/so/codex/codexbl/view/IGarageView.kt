package so.codex.codexbl.view

import so.codex.codexbl.entity.Profile
import so.codex.codexbl.view.base.IBaseView

/**
 * Interface with declared methods for work with Garage View
 */
interface IGarageView : IBaseView {
    /**
     * Show all workspaces in Drawer
     * @param workspaces Workspaces for Drawer
     */
    fun showWorkspaces(workspaces: List<WorkspaceViewModel>)

    /**
     * Show the elem in Drawer for creating new Workspace
     */
    fun showAddWorkspace()

    /**
     * Show header of Drawer
     * @param profile Profile for showing header information
     */
    fun showHeader(profile: Profile)

    data class WorkspaceViewModel(
        val id: String = "",
        val name: String = "",
        val image: String = "",
        val isSelected: Boolean = false
    ) {
        fun hasId(): Boolean = id.isNotEmpty()
        fun hasImage(): Boolean = image.isNotEmpty()
    }
}
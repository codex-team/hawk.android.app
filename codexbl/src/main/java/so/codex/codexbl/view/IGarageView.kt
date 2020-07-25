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

    /**
     * Class-container of workspaces
     * Need for stable using taps in Drawer with them (workspaces)
     * @param id id of workspace
     * @param name name of workspace
     * @param image url image of workspace
     * @param isSelected checks if an item in list was tapped
     * and store this information
     */
    data class WorkspaceViewModel(
        val id: String = "",
        val name: String = "",
        val image: String = "",
        val isSelected: Boolean = false
    ) {
        /**
         * Checks if workspace has id
         */
        fun hasId(): Boolean = id.isNotEmpty()

        /**
         * Checks if workspace has image
         */
        fun hasImage(): Boolean = image.isNotEmpty()
    }
}
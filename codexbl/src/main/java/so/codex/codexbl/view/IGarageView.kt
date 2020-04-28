package so.codex.codexbl.view

import so.codex.codexbl.entity.Profile
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.IBaseView

/**
 * Interface with declared methods for work with Garage View
 */
interface IGarageView : IBaseView {
    /**
     * Show all workspaces in Drawer
     * @param workspaces Workspaces for Drawer
     */
    fun showWorkspaces(workspaces: List<Workspace>)

    /**
     * Show the elem in Drawer for creating new Workspace
     */
    fun showAddWorkspace()

    /**
     * Show header of Drawer
     * @param profile Profile for showing header information
     */
    fun showHeader(profile: Profile)
}
package so.codex.codexbl.view.workspace

import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.ILoaderView

/**
 * Interface with declared method for work with [Workspace] and communication with UI
 */
interface IWorkspaceView : ILoaderView {
    /**
     * Show all projects from list of [Workspace]
     * @param workspaces Workspace representation
     */
    fun showProjects(workspaces: List<Workspace>)

    /**
     * Show text if list of [Workspace] is empty
     */
    fun showEmptyProjects()

    fun openProject(project: Project)
}
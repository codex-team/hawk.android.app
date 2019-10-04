package so.codex.codexbl.view.workspace

import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.ILoaderView

interface IWorkspaceView : ILoaderView {
    fun showWorkspaces(workspace: List<Workspace>)

    fun showEmptyWorkspace()

    fun openProject(project: Project)
}
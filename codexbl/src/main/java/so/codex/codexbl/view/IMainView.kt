package so.codex.codexbl.view

import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.IBaseView

interface IMainView : IBaseView {
    fun showWorkspaces(workspaces: List<Workspace>)
    fun showStartWorkspace()
}
package so.codex.hawk

import so.codex.codexbl.entity.Workspace


interface SelectedWorkspaceListener  {
    fun select(workspace: Workspace)
}
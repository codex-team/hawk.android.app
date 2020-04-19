package so.codex.hawk

import so.codex.codexbl.entity.Workspace

/**
 * An interface which contains a method
 * for selection of workspace
 */
interface SelectedWorkspaceListener  {
    /**
     * Manage current workspace
     * @param workspace given selected workspace
     */
    fun select(workspace: Workspace)
}
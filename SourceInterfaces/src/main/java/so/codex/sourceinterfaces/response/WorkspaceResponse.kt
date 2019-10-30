package so.codex.sourceinterfaces.response

import so.codex.sourceinterfaces.entity.WorkspaceEntity

/**
 * Representation of response from http request on getting information of Workspace.
 * @property workspaceEntity Information of Workspace
 * @see WorkspaceEntity
 * @author Shiplayer
 */
data class WorkspaceResponse<T : WorkspaceEntity>(
    val workspaceEntity: T
)
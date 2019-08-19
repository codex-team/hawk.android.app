package so.codex.sourceinterfaces.response

import so.codex.sourceinterfaces.entity.WorkspaceEntity

data class WorkspaceResponse<T : WorkspaceEntity>(
    val workspaceEntity: T
)
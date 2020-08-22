package so.codex.codexbl.repository.dao

import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

data class WorkspaceResponseDao(val response: List<WorkspaceResponse<WorkspaceEntity>>)
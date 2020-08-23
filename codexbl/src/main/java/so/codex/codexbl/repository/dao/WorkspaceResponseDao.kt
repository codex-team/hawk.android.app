package so.codex.codexbl.repository.dao

import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Date class for storing the response to the request
 * @property response list of [WorkspaceResponse]
 */
data class WorkspaceResponseDao(val response: List<WorkspaceResponse<WorkspaceEntity>>)
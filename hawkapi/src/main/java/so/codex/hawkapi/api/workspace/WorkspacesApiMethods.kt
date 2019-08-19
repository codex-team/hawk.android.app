package so.codex.hawkapi.api.workspace

import io.reactivex.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

interface WorkspacesApiMethods {

    fun getWorkspaces(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>>

}
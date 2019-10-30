package so.codex.hawkapi.api.workspace

import io.reactivex.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Declared methods for sending request only for getting information of Workspace or working with it
 * @author Shiplayer
 */
interface WorkspacesApiMethods {
    /**
     * Get all Workspaces that user have
     * @param token Token
     * @return Observable with [WorkspaceResponse]
     */
    fun getWorkspaces(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>>

}
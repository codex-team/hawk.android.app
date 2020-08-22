package so.codex.hawkapi.api.workspace

import io.reactivex.rxjava3.core.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.OnlyWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Declared methods for sending request only for getting information of Workspace or working with it
 * @author Shiplayer
 */
interface WorkspacesApiMethods {
    /**
     * Get all Workspaces that user have
     * @param token Token
     * @param limit Limited number of workspaces that we can get from the api
     * @param skip Count of workspaces that need to skipped
     * @return Observable with [WorkspaceResponse]
     */
    fun getWorkspaces(
        token: String,
        limit: Int = 30,
        skip: Int = 0
    ): Observable<WorkspaceResponse<FullWorkspaceEntity>>

    /**
     * Get all Workspaces that user have
     * @param token Token
     * @param limit Limited number of workspaces that we can get from the api
     * @param skip Count of workspaces that need to skipped
     * @return Observable with [WorkspaceResponse]
     */
    fun getWorkspacesWithProjects(
        token: String
    ): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>>

    /**
     * Get all Workspaces that user have
     * @param token Token
     * @param limit Limited number of workspaces that we can get from the api
     * @param skip Count of workspaces that need to skipped
     * @return Observable with [WorkspaceResponse]
     */
    fun getOnlyWorkspace(
        token: String,
        limit: Int = 30,
        skip: Int = 0
    ): Observable<WorkspaceResponse<OnlyWorkspaceEntity>>

}
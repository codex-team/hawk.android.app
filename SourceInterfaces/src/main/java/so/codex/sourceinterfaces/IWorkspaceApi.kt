package so.codex.sourceinterfaces

import io.reactivex.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithUsersEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Interface with declared method for sending http requests. The interface is only responsible for getting information
 * of Workspace.
 * @author Shiplayer
 */
interface IWorkspaceApi {
    /**
     * Send request for getting Workspace with common information
     * @param token Access token for identification of user and access to api methods
     * @return [Observable] with response of request
     */
    fun getOnlyWorkspace(token: String): Observable<WorkspaceResponse<WorkspaceEntity>>

    /**
     * Send request for getting Workspace with allowed users
     * @param token Access token for identification of user and access to api methods
     * @return [Observable] with response of request.
     */
    fun getWorkspaceWithUsers(token: String): Observable<WorkspaceResponse<WorkspaceWithUsersEntity>>

    /**
     * Send request for getting Workspace with project information and events
     * @param token Access token for identification of user and access to api methods
     * @return [Observable] with response of request.
     */
    fun getWorkspaceWithProjects(token: String): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>>

    /**
     * Send request for getting Workspace with full information of Workspace with allowed users and projects.
     * @param token Access token for identification of user and access to api methods
     * @return [Observable] with response of request.
     */
    fun getFullWorkspace(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>>
}
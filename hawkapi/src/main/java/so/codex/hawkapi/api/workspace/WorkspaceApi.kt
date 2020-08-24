package so.codex.hawkapi.api.workspace

import io.reactivex.rxjava3.core.Observable
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.subscribeOnIO
import so.codex.sourceinterfaces.IWorkspaceApi
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithUsersEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Implementation of interface [IWorkspaceApi]. Class is singleton with provide method for communication with server
 * and getting information of workspace via sending api request to the server.
 * @constructor Have private constructor for initialize singleton instance of class
 * @author Shiplayer
 */
class WorkspaceApi internal constructor(
    private val service: WorkspacesApiMethods,
    private val userTokenProvider: UserTokenProvider
) : IWorkspaceApi {
    /**
     * Documentation for getting only information about workspace
     */
    override fun getOnlyWorkspace(token: String): Observable<WorkspaceResponse<WorkspaceEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Documentation for getting information about workspace with users
     */
    override fun getWorkspaceWithUsers(token: String): Observable<WorkspaceResponse<WorkspaceWithUsersEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Documentation for getting information about workspace with projects
     */
    override fun getWorkspaceWithProjects(token: String): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>> {
        return service.getWorkspacesWithProjects(token).subscribeOnIO()
    }

    /**
     * Getting all information of Workspace with users and projects.
     * @param token User access token
     * @return [FullWorkspaceEntity] wrapped in [WorkspaceResponse]
     */
    override fun getFullWorkspace(token: String): Observable<out WorkspaceResponse<FullWorkspaceEntity>> {
        return service.getWorkspaces(token).subscribeOnIO()
    }
}

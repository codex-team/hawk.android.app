package so.codex.hawkapi.api.workspace

import io.reactivex.Observable
import so.codex.hawkapi.WorkspaceApiMethodImpl
import so.codex.hawkapi.api.CoreApi
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
class WorkspaceApi private constructor(private val service: WorkspacesApiMethods) : IWorkspaceApi {
    companion object {
        /**
         * Singleton of WorkspaceApi
         */
        val instance by lazy {
            WorkspaceApi(WorkspaceApiMethodImpl(CoreApi.apollo))
        }
    }

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
    override fun getFullWorkspace(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>> {
        return service.getWorkspaces(token).subscribeOnIO()
    }
}

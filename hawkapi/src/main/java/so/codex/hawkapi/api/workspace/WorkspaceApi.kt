package so.codex.hawkapi.api.workspace

import io.reactivex.Observable
import so.codex.hawkapi.WorkspaceApiMethodImpl
import so.codex.hawkapi.api.CoreApi
import so.codex.hawkapi.api.workspace.WorkspaceApi.Companion.instance
import so.codex.hawkapi.subscribeOnIO
import so.codex.sourceinterfaces.IWorkspaceApi
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithUsersEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Является singleton, для получения экземпляра класса необходимо обратиться к переменной [instance].
 * Данный класс предоставляет методы для отправки запросов на сервер.
 * @constructor Приватный конструктор, в котором происходит инициализация сервиса, через который
 * будет отправляться запрос на севрер
 * @author Shiplayer
 */
class WorkspaceApi private constructor(private val service: WorkspacesApiMethods) : IWorkspaceApi {
    companion object {
        val instance by lazy {
            WorkspaceApi(WorkspaceApiMethodImpl(CoreApi.apollo))
        }
    }

    override fun getOnlyWorkspace(token: String): Observable<WorkspaceResponse<WorkspaceEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWorkspaceWithUsers(token: String): Observable<WorkspaceResponse<WorkspaceWithUsersEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWorkspaceWithProjects(token: String): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFullWorkspace(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>> {
        return service.getWorkspaces(token).subscribeOnIO()
    }
}

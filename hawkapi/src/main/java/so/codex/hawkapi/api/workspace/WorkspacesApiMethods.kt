package so.codex.hawkapi.api.workspace

import io.reactivex.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

/**
 * Интерфейс, в котором обявлены методы для взаимодействия с API
 * @author Shiplayer
 */
interface WorkspacesApiMethods {
    /**
     * Получить все Workspace, которые есть у пользователя
     * @param token токен пользователя
     * @return Возвращает Observable с [WorkspaceResponse]
     */
    fun getWorkspaces(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>>

}
package so.codex.sourceinterfaces

import io.reactivex.Observable
import so.codex.sourceinterfaces.entity.FullWorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity
import so.codex.sourceinterfaces.entity.WorkspaceWithUsersEntity
import so.codex.sourceinterfaces.response.WorkspaceResponse

interface IWorkspaceApi {
    fun getOnlyWorkspace(token: String): Observable<WorkspaceResponse<WorkspaceEntity>>

    fun getWorkspaceWithUsers(token: String): Observable<WorkspaceResponse<WorkspaceWithUsersEntity>>

    fun getWorkspaceWithProjects(token: String): Observable<WorkspaceResponse<WorkspaceWithProjectsEntity>>

    fun getFullWorkspace(token: String): Observable<WorkspaceResponse<FullWorkspaceEntity>>
}
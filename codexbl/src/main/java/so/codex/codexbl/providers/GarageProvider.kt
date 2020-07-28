package so.codex.codexbl.providers

import io.reactivex.Observable
import so.codex.sourceinterfaces.IWorkspaceApi
import so.codex.sourceinterfaces.entity.WorkspaceWithProjectsEntity

class GarageProvider(val workspaceApi: IWorkspaceApi) {

    fun refresh() {
        TODO()
        //workspaceApi.getWorkspaceWithProjects()
    }

    fun getWorkspaces(): Observable<WorkspaceWithProjectsEntity> {
        TODO()
    }
}
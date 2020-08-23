package so.codex.codexbl.providers.projects

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.providers.workspaces.WorkspaceProvider

class ProjectProvider(private val workspaceProvider: WorkspaceProvider) {

    fun refresh() {
        workspaceProvider.refresh()
    }

    fun getProjects(): Observable<List<Project>> {
        return workspaceProvider.getSelectedWorkspaceObservable()
            .switchMap { workspace ->
                Log.i("ProjectProvider", "get selected Workspace $workspace")
                if (workspace === Workspace.DEFAULT_WORKSPACE) {
                    workspaceProvider.getListWorkspaceObservable()
                } else {
                    workspaceProvider.getSelectedWorkspaceObservable().map(::listOf)
                }
            }
            .map { workspaceList ->
                Log.i("ProjectProvider", "reduce workspaces")
                workspaceList.map {
                    it.projects
                }.fold(mutableListOf<Project>()) { result, right ->
                    result.addAll(right)
                    result
                }.toList()
            }
    }
}
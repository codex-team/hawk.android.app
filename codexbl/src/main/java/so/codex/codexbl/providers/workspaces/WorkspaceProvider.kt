package so.codex.codexbl.providers.workspaces

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.codexbl.base.SourceRepository
import so.codex.codexbl.base.StorageRepository
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.mappers.toWorkspace
import so.codex.codexbl.repository.dao.WorkspaceResponseDao

class WorkspaceProvider(
    private val storage: StorageRepository<WorkspaceResponseDao>,
    private val sourceRepository: SourceRepository<WorkspaceResponseDao>
) {
    private val selectedWorkspace =
        BehaviorSubject.createDefault<Workspace>(Workspace.DEFAULT_WORKSPACE)

    fun getSelectedWorkspace(): Workspace {
        return selectedWorkspace.value
    }

    fun getSelectedWorkspaceObservable(): Observable<Workspace> {
        return selectedWorkspace.hide()
    }

    fun selectWorkspace(id: String?) {
        if (id == null) {
            selectedWorkspace.onNext(Workspace.DEFAULT_WORKSPACE)
            return
        }
        val dao = storage.load()

        if (dao == null) {
            Log.i("WorkspaceProvider", "Not found workspaces in storage")
            return
        }
        val entity = dao.response.firstOrNull { response ->
            response.workspaceEntity.id == id
        }

        if (entity == null) {
            Log.i("WorkspaceProvider", "Cannot find workspace by id in storage")
        }
    }

    fun refresh() {
        sourceRepository.refresh()
    }

    fun getListWorkspaceObservable(): Observable<List<Workspace>> {
        return sourceRepository.getObservable().map { dao ->
            dao.response.map { response ->
                response.workspaceEntity.toWorkspace()
            }
        }
    }
}
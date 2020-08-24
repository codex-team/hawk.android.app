package so.codex.codexbl.providers.workspaces

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.codexbl.base.SourceRepository
import so.codex.codexbl.base.StorageRepository
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.mappers.toWorkspace
import so.codex.codexbl.repository.dao.WorkspaceResponseDao

/**
 * Provide all information about workspace from two repositories
 * @param storage cache storage for providing information if something occurred and we can get
 * information from source storage
 * @param sourceRepository provide information from something
 */
class WorkspaceProvider(
    private val storage: StorageRepository<WorkspaceResponseDao>,
    private val sourceRepository: SourceRepository<WorkspaceResponseDao>
) {

    /**
     * Current selected workspace. By default selected empty [Workspace]
     */
    private val selectedWorkspace =
        BehaviorSubject.createDefault<Workspace>(Workspace.DEFAULT_WORKSPACE)

    /**
     * Get current selected [Workspace]
     * @return Selected [Workspace]
     */
    fun getSelectedWorkspace(): Workspace {
        return selectedWorkspace.value
    }

    /**
     * Get selected [Workspace] in all time
     * @return Stream of selected [Workspace]
     */
    fun getSelectedWorkspaceObservable(): Observable<Workspace> {
        return selectedWorkspace.hide()
    }

    /**
     * Setup selected [Workspace]
     * @param id of [Workspace]
     */
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

    /**
     * Refresh workspaces
     */
    fun refresh() {
        sourceRepository.refresh()
    }

    /**
     * Get list of Workspaces
     * @return Stream of list of workspaces
     */
    fun getListWorkspaceObservable(): Observable<List<Workspace>> {
        return sourceRepository.getObservable().map { dao ->
            dao.response.map { response ->
                response.workspaceEntity.toWorkspace()
            }
        }
    }
}
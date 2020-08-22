package so.codex.codexbl.providers.workspaces

import android.util.Log
import so.codex.codexbl.base.SourceRepository
import so.codex.codexbl.base.StorageRepository
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.repository.dao.WorkspaceResponseDao

class WorkspaceProvider(
    private val storage: StorageRepository<WorkspaceResponseDao>,
    private val sourceRepository: SourceRepository<WorkspaceResponseDao>
) {
    private var selectedWorkspace: Workspace? = null

    fun selectWorkspace(id: String) {
        val dao = storage.load()
        if (dao == null) {
            Log.i("WorkspaceProvider", "cannot setup selected workspace")
        }
    }

    fun getListWorkspaceObservable() {
        sourceRepository.getObservable().map { dao ->
            dao.response.map { response ->
                response.workspaceEntity
            }
        }
    }
}
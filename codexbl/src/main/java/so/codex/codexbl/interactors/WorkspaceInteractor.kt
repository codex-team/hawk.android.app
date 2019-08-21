package so.codex.codexbl.interactors

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.Workspace
import so.codex.sourceinterfaces.IWorkspaceApi

class WorkspaceInteractor : RefreshableInteractor(), IWorkspaceInteractor, KoinComponent {
    private val api: IWorkspaceApi by inject()

    override fun getWorkspaces(): Single<List<Workspace>> {
        return api.getFullWorkspace(getToken())
            .refreshToken()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Workspace(
                    it.workspaceEntity.id,
                    it.workspaceEntity.name,
                    it.workspaceEntity.description ?: "",
                    it.workspaceEntity.image
                )
            }.toList()
    }

    override fun getWorkspacesObservable(): Observable<List<Workspace>> {
        TODO()
    }
}
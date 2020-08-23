package so.codex.codexbl.interactors

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
import so.codex.hawkapi.exceptions.InternalServerErrorException
import so.codex.sourceinterfaces.IWorkspaceApi

/**
 * Class for communication with repositories or api that provide methods for getting necessary information
 * of Workspace.
 * @author Shiplayer
 */
class WorkspaceInteractor : RefreshableInteractor(), IWorkspaceInteractor, KoinComponent {
    /**
     * Workspace
     */
    private val api: IWorkspaceApi by inject()

    /**
     * Send request to get workspace
     */
    override fun getWorkspaces(): Single<List<Workspace>> {
        return api.getFullWorkspace(token)
            .refreshToken()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Workspace(
                    it.workspaceEntity.id,
                    it.workspaceEntity.name,
                    it.workspaceEntity.description ?: "",
                    it.workspaceEntity.image,
                    it.workspaceEntity.projects.map {
                        Project(
                            it.id,
                            it.token,
                            it.name,
                            it.description,
                            it.url,
                            it.image
                        )
                    }
                )
            }.toList()
            .onErrorResumeNext {
                if (it is InternalServerErrorException) {
                    Single.just(emptyList())
                } else {
                    Single.error(it)
                }
            }
    }

    override fun selectWorkspace(id: String) {
        TODO("Not yet implemented")
    }

    /**
     * Todo
     */
    override fun getWorkspacesObservable(): Observable<List<Workspace>> {
        TODO()
    }
}
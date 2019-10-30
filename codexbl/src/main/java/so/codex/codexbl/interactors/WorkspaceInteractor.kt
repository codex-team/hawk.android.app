package so.codex.codexbl.interactors

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.Event
import so.codex.codexbl.entity.Payload
import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
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
                                        it.image,
                                        it.events.map {
                                            Event(
                                                    it.id,
                                                    it.catcherType,
                                                    it.payload.let {
                                                        Payload(
                                                                it.title,
                                                                it.timestamp
                                                        )
                                                    }
                                            )
                                        }
                                )
                            }
                    )
                }.toList()
    }

    /**
     * Todo
     */
    override fun getWorkspacesObservable(): Observable<List<Workspace>> {
        TODO()
    }
}
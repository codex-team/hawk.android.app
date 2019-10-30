package so.codex.codexbl.interactors

import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexbl.entity.Workspace

/**
 * Interface with declared methods for work with [Workspace] and communication
 * @author Shiplayer
 */
interface IWorkspaceInteractor {
    /**
     * Get Workspaces wrapped in Observable
     */
    fun getWorkspacesObservable(): Observable<List<Workspace>>

    /**
     * Get Workspaces
     */
    fun getWorkspaces(): Single<List<Workspace>>
}
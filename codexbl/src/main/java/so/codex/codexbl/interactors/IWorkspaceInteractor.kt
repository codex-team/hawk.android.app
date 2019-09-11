package so.codex.codexbl.interactors

import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexbl.entity.Workspace

/**
 * Интерфейс, в котором определены методы необходимые для работы с workspace
 * @author Shiplayer
 */
interface IWorkspaceInteractor {
    fun getWorkspacesObservable(): Observable<List<Workspace>>

    fun getWorkspaces(): Single<List<Workspace>>
}
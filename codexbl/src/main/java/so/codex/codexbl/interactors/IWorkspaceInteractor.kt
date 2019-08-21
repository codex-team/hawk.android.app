package so.codex.codexbl.interactors

import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexbl.entity.Workspace

interface IWorkspaceInteractor {
    fun getWorkspacesObservable(): Observable<List<Workspace>>

    fun getWorkspaces(): Single<List<Workspace>>
}
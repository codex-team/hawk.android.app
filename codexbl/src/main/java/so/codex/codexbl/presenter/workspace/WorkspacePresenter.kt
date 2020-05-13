package so.codex.codexbl.presenter.workspace

import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.LoaderPresenter
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.view.workspace.IWorkspaceView

/**
 * Presentor that responsible on event from Workspace.
 * @author Shiplayer
 */
class WorkspacePresenter : LoaderPresenter<IWorkspaceView>(), KoinComponent {
    /**
     * Interactor for work with Workspaces
     */
    private val workspaceInteractor: IWorkspaceInteractor by inject()

    override fun onAttach() {
        super.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * Getting all workspaces
     */
    fun loadAllWorkspaces() {
        compositeDisposable.of(
                workspaceInteractor
                        .getWorkspaces()
                    .doOnSubscribe {
                        Log.i("WorkspacePresenter", "subscribe getWorkspace")
                    }
                    .doOnDispose {
                        Log.i("WorkspacePresenter", "dispose getWorkspace")
                    }
                        .attachLoader()
                        .subscribe({
                            if (!it.isNullOrEmpty())
                                view?.showProjects(it)
                            else
                                view?.showEmptyProjects()
                        }, {
                            it.printStackTrace()
                            view?.showErrorMessage(it.message ?: " error")
                        })
        )
    }

    /**
     * Dispose of all RxJava Streams/Calls
     */
    fun unsubscribe() {
        compositeDisposable.dispose()
    }
}
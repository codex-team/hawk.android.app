package so.codex.codexbl.presenter

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.view.IMainView

class MainPresenter : BasePresenter<IMainView>(), KoinComponent {
    private val workspaceInteractor: IWorkspaceInteractor by inject()

    /**
     * Getting all workspaces
     */
    fun loadAllWorkspaces() {
        compositeDisposable.of(
            workspaceInteractor
                .getWorkspaces()
                .subscribe({
                    if (!it.isNullOrEmpty())
                        view?.showWorkspaces(it)
                    view?.showStartWorkspace()
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
package so.codex.codexbl.presenter.workspace

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.LoaderPresenter
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.view.workspace.IWorkspaceView

class WorkspacePresenter : LoaderPresenter<IWorkspaceView>(), KoinComponent {
    private val workspaceInteractor: IWorkspaceInteractor by inject()

    override fun onAttach() {
        super.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun loadAllWorkspaces() {
        compositeDisposable.of(
            workspaceInteractor
                .getWorkspaces()
                .attachLoader()
                .subscribe({
                    if (!it.isNullOrEmpty())
                        view?.showWorkspaces(it)
                    else
                        view?.showEmptyWorkspace()
                }, {
                    it.printStackTrace()
                    view?.showErrorMessage(it.message ?: " error")
                })
        )
    }

    fun unsubscribe() {
        compositeDisposable.dispose()
    }
}
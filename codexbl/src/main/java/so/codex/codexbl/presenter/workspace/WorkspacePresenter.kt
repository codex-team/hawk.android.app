package so.codex.codexbl.presenter.workspace

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.view.workspace.IWorkspaceView

class WorkspacePresenter : BasePresenter<IWorkspaceView>(), KoinComponent {
    private val workspaceInteractor: IWorkspaceInteractor by inject()



    override fun onAttach() {
        super.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun getAllWorkspaces() {

    }
}
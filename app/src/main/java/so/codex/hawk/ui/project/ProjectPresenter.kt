package so.codex.hawk.ui.project

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.ReactiveBasePresenter
import so.codex.codexbl.interactors.projects.IProjectInteractor

class ProjectPresenter :
    ReactiveBasePresenter<IProjectView, IProjectView.ProjectViewModel, IProjectView.UiEvent>(),
    KoinComponent {

    private val interactor: IProjectInteractor by inject()

    override fun onAttach() {
        super.onAttach()
        interactor.getProjects().subscribe {

            view?.showUi(
                IProjectView.ProjectViewModel(
                    it,
                    false
                )
            )
        }
    }

    override fun submitUiEvent(event: IProjectView.UiEvent) {
        if (event is IProjectView.UiEvent.Refresh) {
            interactor.refresh()
        }
    }
}
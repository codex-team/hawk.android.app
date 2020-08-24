package so.codex.hawk.ui.project

import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.ReactiveBasePresenter
import so.codex.codexbl.interactors.projects.IProjectInteractor

/**
 * Class for handling data from business and preparing for display
 */
class ProjectPresenter :
    ReactiveBasePresenter<IProjectView, IProjectView.ProjectViewModel, IProjectView.UiEvent>(),
    KoinComponent {

    /**
     * Interactor used for getting all projects for showing on screen
     */
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
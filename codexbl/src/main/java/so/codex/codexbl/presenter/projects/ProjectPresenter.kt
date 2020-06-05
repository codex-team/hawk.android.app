package so.codex.codexbl.presenter.projects

import io.reactivex.Observable
import org.koin.core.KoinComponent
import so.codex.codexbl.base.ReactiveBasePresenter
import so.codex.codexbl.view.projects.IProjectView

class ProjectPresenter :
    ReactiveBasePresenter<IProjectView, IProjectView.ProjectViewModel, IProjectView.UiEvent>(),
    KoinComponent {


    override fun subscribe(): Observable<IProjectView.ProjectViewModel> {
        TODO()
    }

    override fun submitEvent(event: IProjectView.UiEvent) {
        TODO()
    }

}
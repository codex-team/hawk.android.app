package so.codex.codexbl.view.projects

import so.codex.codexbl.entity.Project
import so.codex.codexbl.view.base.IReactiveBaseView

interface IProjectView : IReactiveBaseView {

    //fun submitEvent(event: UiEvent)

    data class ProjectViewModel(
        val projects: List<Project>,
        val showLoader: Boolean
    )

    sealed class UiEvent : IReactiveBaseView.UiEvent {
        class UpdateProjects(val id: String? = null) : UiEvent()
    }
}
package so.codex.hawk.ui.project

import so.codex.codexbl.entity.Project
import so.codex.codexbl.view.base.IReactiveBaseView

/**
 * Representation of profile view
 */
interface IProjectView : IReactiveBaseView<IProjectView.ProjectViewModel, IProjectView.UiEvent> {

    //fun submitEvent(event: UiEvent)

    /**
     * View model for showing in project view
     * @property projects list of projects [Project]
     * @property showLoader need to show loader
     */
    data class ProjectViewModel(
        val projects: List<Project>,
        val showLoader: Boolean
    )

    /**
     * Ui events for reaction something action in project view
     */
    sealed class UiEvent : IReactiveBaseView.UiEvent {

        /**
         * Select some project
         * @property id ID of selected project or null, if we unselected project
         */
        class ClickOnProject(val id: String? = null) : UiEvent()

        /**
         * Event for updating data on screen
         */
        object Refresh : UiEvent()
    }
}
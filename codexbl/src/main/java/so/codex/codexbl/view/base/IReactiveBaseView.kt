package so.codex.codexbl.view.base

import io.reactivex.rxjava3.core.Observable

/**
 * Interface for reactive action and handle some date from somewhere
 */
interface IReactiveBaseView<M, E : IReactiveBaseView.UiEvent> : IBaseView {

    /**
     * Method that receive and handle model for representation on ui components
     * @param model data representation model for showing on ui
     */
    fun showUi(model: M)

    /**
     * Method for getting event
     * @return stream of event of user interaction or program events
     */
    fun observeUiEvent(): Observable<UiEvent>


    /**
     * Interface that describe action of user
     */
    interface UiEvent
}
package so.codex.codexbl.view.base

import io.reactivex.rxjava3.core.Observable

/**
 * Interface for reactive action and handle some date from somewhere
 */
interface IReactiveBaseView<M, E : IReactiveBaseView.UiEvent> : IBaseView {

    fun showUi(model: M)

    fun observeUiEvent(): Observable<UiEvent>


    /**
     * Interface that describe action of user
     */
    interface UiEvent
}
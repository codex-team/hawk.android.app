package so.codex.codexbl.base

import so.codex.codexbl.view.base.IReactiveBaseView

/**
 * Reactive alternative of simple [BasePresenter], class have two additional generic type for sending
 * view model for showing and event that need to handle it
 */
abstract class ReactiveBasePresenter<V, M, E> : BasePresenter<V>()
    where V : IReactiveBaseView<M, E>, E : IReactiveBaseView.UiEvent {

    /**
     * Handle ui event in presentation layer
     * @param event Is event that occurred after user interactions
     */
    abstract fun submitUiEvent(event: E)
}
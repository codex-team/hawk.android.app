package so.codex.codexbl.base

import so.codex.codexbl.view.base.IReactiveBaseView

abstract class ReactiveBasePresenter<V, M, E> : BasePresenter<V>()
    where V : IReactiveBaseView<M, E>, E : IReactiveBaseView.UiEvent {
    abstract fun submitUiEvent(event: E)
}
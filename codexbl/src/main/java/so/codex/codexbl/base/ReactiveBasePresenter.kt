package so.codex.codexbl.base

import io.reactivex.Observable
import so.codex.codexbl.view.base.IReactiveBaseView

abstract class ReactiveBasePresenter<V, M, E> : BasePresenter<V>()
        where V : IReactiveBaseView, E : IReactiveBaseView.UiEvent {
    abstract fun subscribe(): Observable<M>

    abstract fun submitEvent(event: E)

}
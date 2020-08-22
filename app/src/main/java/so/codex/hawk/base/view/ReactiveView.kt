package so.codex.hawk.base.view

import io.reactivex.rxjava3.core.Observable
import so.codex.codexbl.view.base.IBaseView

interface ReactiveView<M, E> : IBaseView {
    fun showUi(model: M)
    fun submitEvent(event: E)
    fun observeUiEvent(): Observable<E>
}
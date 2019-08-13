package so.codex.codexbl.base

import so.codex.codexbl.view.base.IBaseView

abstract class BasePresenter<V> where V : IBaseView {
    var view: V? = null
        private set(value) {
            field = value
        }

    fun attached(view: V) {
        this.view = view
    }

    fun detached() {
        this.view = null
    }

    open fun onAttach() {}
    open fun onDetach() {}
}
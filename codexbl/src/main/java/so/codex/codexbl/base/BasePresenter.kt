package so.codex.codexbl.base

import so.codex.codexbl.view.base.IBaseView

/**
 * Base presenter with common method for other presenters. Presenter can connected to view via
 * interface [IBaseView] for communication to UI, like as showing error and handling user or custom
 * events.
 * @author Shiplayer
 */
abstract class BasePresenter<V> where V : IBaseView {
    /**
     * Composition disposable that can to contain all instance of disposable and dispose them if
     * it need
     */
    protected val compositeDisposable = CompositeDisposable()

    /**
     * Interface that representation communication with ui
     */
    var view: V? = null
        private set(value) {
            field = value
        }

    /**
     * Attach [view] to presenter. This method should be called after [view] was created and showed
     * on screen for communication with it.
     */
    fun attached(view: V) {
        this.view = view
        onAttach()
    }

    /**
     * Assign null to view if [view] is destroyed or hidden.
     */
    fun detached() {
        this.view = null
        onDetach()
    }

    /**
     * Event that invoked if [view] is attached to presenter.
     */
    open fun onAttach() {}

    /**
     * Event that invoked if [view] is destroyed
     */
    open fun onDetach() {}
}
package so.codex.codexbl.base

import so.codex.codexbl.view.base.IBaseView

/**
 * Основной презентр, в котором определяются методы для всех остальных. Презентор может
 * присоединится к виью через интерфейс [IBaseView], благодаря которому он может взаимодействоваться
 * с UI, показывать ошибки и обрабатывать пользовательские события
 * @author Shiplayer
 */
abstract class BasePresenter<V> where V : IBaseView {
    protected val compositeDisposable = CompositeDisposable()
    var view: V? = null
        private set(value) {
            field = value
        }

    /**
     * Присоединить [view] к презентору. Означает, что [view] уже создано и отображено на экране и
     * с ней уже можно взаимодействовать
     */
    fun attached(view: V) {
        this.view = view
        onAttach()
    }

    /**
     * Присваевает null к [view], т.к. View скрыто или уничтожено
     */
    fun detached() {
        this.view = null
        onDetach()
    }

    /**
     * Событие, которое вызываются, когда вью присоединилась к презентору
     */
    open fun onAttach() {}

    /**
     * Событие, которое вызываются, когда вью уничтожелась
     */
    open fun onDetach() {}
}
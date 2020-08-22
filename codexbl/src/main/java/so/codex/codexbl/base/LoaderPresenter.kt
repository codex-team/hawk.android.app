package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import so.codex.codexbl.view.base.ILoaderView

/**
 * The presenter that can communicate to [ILoaderView] for showing and hiding loader element.
 * @author Shiplayer
 */
abstract class LoaderPresenter<V> : BasePresenter<V>() where V : ILoaderView {

    /**
     * Attach observable to loader then it subscribed and show loader else hidden it.
     * If error occurred, then hide loader and invoke method [ILoaderView.showErrorMessage]
     */
    fun <T> Observable<T>.attachLoader(): Observable<T> =
            doOnSubscribe {
                view?.showLoader()
            }.doOnComplete {
                view?.hideLoader()
            }.doOnError {
                view?.hideLoader()
                view?.showErrorMessage(it.message ?: "")
            }

    /**
     * Attach single to loader then it subscribed and show loader else hidden it.
     * If error occurred, then hide loader and invoke method [ILoaderView.showErrorMessage]
     */
    fun <T> Single<T>.attachLoader(): Single<T> =
            doOnSubscribe {
                view?.showLoader()
            }.doAfterSuccess {
                view?.hideLoader()
            }.doOnError {
                view?.hideLoader()
                view?.showErrorMessage(it.message ?: "")
            }
}
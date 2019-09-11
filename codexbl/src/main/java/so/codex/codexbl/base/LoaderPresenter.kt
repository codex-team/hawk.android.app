package so.codex.codexbl.base

import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexbl.view.base.ILoaderView

/**
 * Презентор, в котором происходит взаимодействие с элементом [ILoaderView].
 * @author Shiplayer
 */
abstract class LoaderPresenter<V> : BasePresenter<V>() where V : ILoaderView {

    /**
     * Присоединить к [Observable] лоадер, когда он подпишется, показать лоадер, иначе скрыть его.
     * Если возникла ошибка, то скрыть лоадер и вызвать [ILoaderView.showErrorMessage]
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
     * Присоединить к [Single] лоадер, когда он подпишется, показать лоадер, иначе скрыть его.
     * Если возникла ошибка, то скрыть лоадер и вызвать [ILoaderView.showErrorMessage]
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
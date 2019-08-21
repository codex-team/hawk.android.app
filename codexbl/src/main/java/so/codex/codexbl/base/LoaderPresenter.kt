package so.codex.codexbl.base

import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexbl.view.base.ILoaderView

abstract class LoaderPresenter<V> : BasePresenter<V>() where V : ILoaderView {

    fun <T> Observable<T>.attachLoader(): Observable<T> =
        doOnSubscribe {
            view?.showLoader()
        }.doOnComplete {
            view?.hideLoader()
        }.doOnError {
            view?.hideLoader()
            view?.showErrorMessage(it.message ?: "")
        }

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
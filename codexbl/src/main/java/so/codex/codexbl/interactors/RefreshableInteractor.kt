package so.codex.codexbl.interactors

import android.util.Log
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.exceptions.NoAuthorizedException
import so.codex.codexbl.interactors.interfaces.IRefreshableInteractor
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.sourceinterfaces.IAuthApi
import java.util.concurrent.atomic.AtomicReference

/**
 * Interactor can refreshed token, if occurred error [AccessTokenExpiredException] in while
 * sending request, if interator can to update token, then update it in storage and repeat request
 * with new token.
 * @constructor Create interactor and set up token used from last session
 * @throws NoAuthorizedException throw exception, if in initialized token was empty
 * @author Shiplayer
 */
open class RefreshableInteractor : IRefreshableInteractor, KoinComponent {
    companion object {
        /**
         * Used token in the current moment
         */
        private val atomicToken = AtomicReference<String>("")

    }

    init {
        Log.i(this::class.java.simpleName, "create new instance of refreshableInteractor")
    }

    /**
     * Interactor for getting last session
     */
    private val userInteractor: IUserInteractor by inject()

    /**
     * Token provider for getting current token and request update old token
     */
    private val userTokenProvider: UserTokenProvider by inject()

    /**
     * Api for refreshing access token
     */
    private val authApi: IAuthApi by inject()

    init {
        atomicToken.set(
            userInteractor.getLastSession()?.accessToken ?: throw NoAuthorizedException()
        )
    }

    /**
     * Get token from [atomicToken]
     */
    //TODO костыль, сделать нормально
    final override val token: String
        get() = atomicToken.get()

    /**
     * Retry observable if throwable is occurred, and this throwable is [AccessTokenExpiredException],
     * update token and save it in storage
     */
    override fun <T> Observable<T>.refreshToken(): Observable<T> {
        var first = false
        return retryWhen {
            it.flatMap {
                Log.i("RefreshableInteractor", "error ${it::class.java.simpleName}")
                if (it is AccessTokenExpiredException && it.token != null && !first) {
                    first = true
                    userTokenProvider.updateToken(it.token?.refreshToken ?: "")
                    userTokenProvider.getTokenObservable()
                } else {
                    Observable.error(it)
                }
            }.doOnNext {
                Log.i("RefreshableInteractor", "Already to next")
            }
        }
    }

    /**
     * Retry single if throwable is occurred, and this throwable is [AccessTokenExpiredException],
     * update token and save it in storage
     */
    // Необходимо будет проверить, правильно ли работает данная реализация
    override fun <T> Single<T>.refreshTokenSingle(): Single<T> {
        return retryWhen {
            it.flatMap {
                if (it is AccessTokenExpiredException && userInteractor.getLastSession() != null) {
                    userTokenProvider.updateToken(it.token?.refreshToken ?: "")
                    userTokenProvider.getTokenObservable().toFlowable(BackpressureStrategy.LATEST)
                } else {
                    Flowable.error(it)
                }
            }
        }
    }

}
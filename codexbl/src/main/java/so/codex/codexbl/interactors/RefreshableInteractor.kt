package so.codex.codexbl.interactors

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.exceptions.NoAuthorizedException
import so.codex.codexbl.interactors.interfaces.IRefreshableInteractor
import so.codex.core.entity.SessionData
import so.codex.core.entity.UserToken
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import java.util.concurrent.TimeUnit
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

    /**
     * Interactor for getting last session
     */
    private val userInteractor: IUserInteractor by inject()

    private val refreshInteractor = RefreshInteractor()

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
                Log.i(
                    "RefreshToken",
                    "it is AccessTokenExpiredException ${it is AccessTokenExpiredException}"
                )
                if (it is AccessTokenExpiredException)
                    Log.i(
                        "RefreshToken",
                        "it.token != null && !first ${it.token} && ${!first}"
                    )
                if (it is AccessTokenExpiredException && it.token != null && !first) {
                    first = true
                    refreshInteractor.refreshToken(it.token!!)
                        .doOnNext {
                            Log.i(
                                "RefreshableInteractor",
                                "update atomicToken ${it.accessToken} and refresh atomicToken ${it.refreshToken}"
                            )
                            userInteractor.updateToken(
                                UserToken(
                                    it.accessToken,
                                    it.refreshToken
                                )
                            )
                        }.delay(100, TimeUnit.MILLISECONDS)
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
                    authApi.refreshToken(TokenEntity(userInteractor.getLastSession()!!.refreshToken))
                        .doOnSuccess {
                            val lastSession = userInteractor.getLastSession()!!
                            userInteractor.saveSession(
                                SessionData(
                                    lastSession.email,
                                    it.accessToken,
                                    it.refreshToken
                                )
                            )
                        }.toFlowable()
                } else {
                    Flowable.error(it)
                }
            }
        }
    }

}
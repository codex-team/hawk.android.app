package so.codex.codexbl.interactors

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.exceptions.NoAuthorizedException
import so.codex.codexbl.interactors.interfaces.IRefreshableInteractor
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

    /**
     * Api for refreshing access token
     */
    private val authApi: IAuthApi by inject()

    init {
        atomicToken.set(
                userInteractor.getLastSession()?.accessToken ?: throw NoAuthorizedException()
        )
    }

    //TODO костыль, сделать нормально
    final override val token: String
        get() = atomicToken.get()

    override fun <T> Observable<T>.refreshToken(): Observable<T> {
        var first = false
        return retryWhen {
            it.flatMap {
                if (it is AccessTokenExpiredException && token.isNotEmpty() && !first) {
                    first = true
                    val lastSession = userInteractor.getLastSession()!!
                    authApi.refreshToken(TokenEntity(lastSession.refreshToken))
                            .doOnSuccess {
                                Log.i(
                                        "RefreshableInteractor",
                                        "update atomicToken ${it.accessToken} and refresh atomicToken ${it.refreshToken}"
                                )
                                atomicToken.set(it.accessToken)
                                userInteractor.saveSession(
                                        SessionData(
                                                lastSession.email,
                                                it.accessToken,
                                                it.refreshToken
                                        )
                                )
                            }.delay(100, TimeUnit.MILLISECONDS).toObservable()
                } else {
                    Observable.error(it)
                }
            }.doOnNext {
                Log.i("RefreshableInteractor", "Already to next")
            }
        }
    }

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
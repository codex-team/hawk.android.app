package so.codex.codexbl.interactors

import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.exceptions.NoAuthorizedException
import so.codex.codexbl.interactors.interfaces.IRefreshableInteractor
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import java.util.concurrent.atomic.AtomicReference

open class RefreshableInteractor() : IRefreshableInteractor, KoinComponent {
    companion object {
        val token = AtomicReference<String>("")
    }

    private val userInteractor: IUserInteractor by inject()
    private val authApi: IAuthApi by inject()

    init {
        token.set(userInteractor.getLastSession()?.accessToken ?: throw NoAuthorizedException())
    }

    //TODO костыль, сделать нормально
    override fun getToken(): String = token.get()

    override fun <T> Observable<T>.refreshToken(): Observable<T> {
        return retryWhen {
            it.flatMap {
                if (it is AccessTokenExpiredException && token.get().isNotEmpty()) {
                    authApi.refreshToken(TokenEntity(userInteractor.getLastSession()!!.refreshToken))
                            .doOnSuccess {
                                val lastSession = userInteractor.getLastSession()!!
                                token.set(it.accessToken)
                                userInteractor.saveSession(
                                        SessionData(
                                                lastSession.email,
                                                it.accessToken,
                                                it.refreshToken
                                        )
                                )
                            }.toObservable()
                } else {
                    Observable.error(it)
                }
            }
        }
    }

    override fun <T> Observable<T>.refreshTokenSingle(): Observable<T> {
        return retryWhen {
            it.flatMap {
                if(it is AccessTokenExpiredException && userInteractor.getLastSession() != null){
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
                            }.toObservable()
                } else {
                    Observable.error(it)
                }
            }
        }
    }

}
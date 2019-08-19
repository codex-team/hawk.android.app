package so.codex.codexbl.interactors

import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.exceptions.NoAuthorizedException
import so.codex.codexbl.interactors.interfaces.IRefreshableInteractor
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity

open class RefreshableInteractor() : IRefreshableInteractor, KoinComponent {
    private val userInteractor: IUserInteractor by inject()
    private val authApi: IAuthApi by inject()

    override fun getToken(): String =
            userInteractor.getLastSession()?.accessToken ?: throw NoAuthorizedException()

    override fun <T> Observable<T>.refreshToken(): Observable<T> {
        return retryWhen {
            it.flatMap {
                if(it is AccessTokenExpiredException && userInteractor.getLastSession() != null){
                    authApi.refreshToken(TokenEntity(userInteractor.getLastSession()!!.refreshToken)).toObservable()
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
                    authApi.refreshToken(TokenEntity(userInteractor.getLastSession()!!.refreshToken)).toObservable()
                } else {
                    Observable.error(it)
                }
            }
        }
    }

}
package so.codex.codexbl.interactors

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.core.entity.UserToken
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

class RefreshInteractor : KoinComponent {
    private val subject = PublishSubject.create<UserToken>()

    private val authApi: IAuthApi by inject()

    private val tokenObserver = subject
        .doOnNext {
            info("onNext $it")
        }.distinct().flatMap {
            info("refreshToken")
            authApi.refreshToken(TokenEntity(it.refreshToken)).toObservable()
        }.replay().autoConnect()

    init {
        tokenObserver.subscribe()
    }

    fun refreshToken(token: UserToken): Observable<TokenResponse> {
        info("refreshToken with token = $token")
        if (token.accessToken.isNotEmpty())
            subject.onNext(token)
        return tokenObserver
    }

    fun RefreshInteractor.info(message: String) {
        Log.i(this::class.java.simpleName, message)
    }

    /*fun provideTokenRefresh(): Observable<TokenResponse> {
    }*/

    data class AccessToken(val accessToken: String = "")
}
package so.codex.codexbl.providers

import android.util.Log
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import so.codex.core.UserTokenDAO
import so.codex.core.UserTokenProvider
import so.codex.core.entity.UserToken
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

class UserTokenProviderImpl(
    val userTokenDAO: UserTokenDAO,
    authApi: IAuthApi
) : UserTokenProvider {
    private val tokenEventSubject = BehaviorSubject.createDefault(TokenEvent.TOKEN_VALID)
    private val tokenUpdatePublisher = PublishSubject.create<String>()
    private val tokenSubject =
        BehaviorSubject.createDefault(userTokenDAO.getUserToken() ?: UserToken.EMPTY_TOKEN)

    init {
        tokenUpdatePublisher
            .doOnNext {
                info("tokenUpdatePublisher $it")
            }
            .distinct()
            .filter {
                val filterVal =
                    tokenEventSubject.hasValue() && tokenEventSubject.value!! == TokenEvent.TOKEN_UPDATE
                info("tokenUpdatePublisher.filter $filterVal")
                filterVal
            }
            .flatMap {
                authApi.refreshTokenObservable(TokenEntity(it))
            }.subscribe({
                info("tokenUpdatePublisher.subscribe $it")
                processTokenResponse(it)
            }, {
                Log.e("UserTokenProvider", it.message, it)
            })
    }

    private fun processTokenResponse(response: TokenResponse) {
        val userToken = UserToken(response.accessToken, response.refreshToken)
        userTokenDAO.saveUserToken(userToken)
        tokenSubject.onNext(userToken)
        tokenEventSubject.onNext(TokenEvent.TOKEN_VALID)
    }

    override fun getToken(): UserToken {
        val tokenEvent = tokenEventSubject.value ?: TokenEvent.TOKEN_INVALID
        return processTokenEvent(tokenEvent)
    }

    override fun getTokenSingle(): Observable<UserToken> {
        return tokenEventSubject
            .flatMap {
                info("getTokenSingle flatMap $it")
                Observable.just(processTokenEvent(it))
            }
            .doOnNext {
                info("getTokenSingle next $it")
            }
    }

    private fun processTokenEvent(tokenEvent: TokenEvent): UserToken {
        return when (tokenEvent) {
            TokenEvent.TOKEN_INVALID -> {
                UserToken.EMPTY_TOKEN
            }
            TokenEvent.TOKEN_UPDATE -> {
                tokenSubject.skip(1).blockingNext().first()
            }
            TokenEvent.TOKEN_VALID -> {
                tokenSubject.value ?: UserToken.EMPTY_TOKEN
            }
        }
    }

    override fun updateToken(refreshToken: String) {
        info("updateToken $refreshToken")
        if (refreshToken.isEmpty()) {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)
        tokenUpdatePublisher.onNext(refreshToken)
    }

    override fun updateToken(userToken: UserToken) {
        info("updateToken $userToken")
        if (userToken == UserToken.EMPTY_TOKEN || userToken.refreshToken == "") {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)
        tokenUpdatePublisher.onNext(userToken.refreshToken)
    }

    private enum class TokenEvent {
        TOKEN_VALID,
        TOKEN_UPDATE,
        TOKEN_INVALID
    }

    fun info(message: String) {
        Log.i(this::class.java.simpleName, message)
    }
}
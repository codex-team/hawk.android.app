package so.codex.codexbl.providers

import android.annotation.SuppressLint
import android.util.Log
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.ship.hawk.utils.commons.info
import so.codex.core.UserTokenDAO
import so.codex.core.UserTokenProvider
import so.codex.core.entity.UserToken
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 *
 * @param userTokenDAO - storage that can save and load user token
 * @param authApi - api for updating user token
 */
@SuppressLint("CheckResult")
class UserTokenProviderImpl(
    private val userTokenDAO: UserTokenDAO,
    authApi: IAuthApi
) : UserTokenProvider {

    /**
     * An event stream that represents state of token
     */
    private val tokenEventSubject = BehaviorSubject.createDefault(TokenEvent.TOKEN_VALID)

    /**
     * Stream of refresh token for updating session
     */
    private val tokenUpdatePublisher = PublishRelay.create<String>()

    /**
     * Stream of already updated token
     */
    private val tokenSubject = BehaviorSubject.createDefault(
        userTokenDAO.getUserToken() ?: UserToken.EMPTY_TOKEN
    )

    /**
     * subscribe of stream with refreshed token, update session if refresh token was changed
     */
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

    /**
     * Save token response and update all streams
     * @param response [TokenResponse] that we receive response after update token
     */
    private fun processTokenResponse(response: TokenResponse) {
        val userToken = UserToken(response.accessToken, response.refreshToken)
        userTokenDAO.saveUserToken(userToken)
        tokenSubject.onNext(userToken)
        tokenEventSubject.onNext(TokenEvent.TOKEN_VALID)
    }

    /**
     * Get user token by event
     * @return [UserToken] that have in cache or give token after updating
     */
    override fun getToken(): UserToken {
        val tokenEvent = tokenEventSubject.value ?: TokenEvent.TOKEN_INVALID
        return processTokenEvent(tokenEvent)
    }

    /**
     * Listening event stream and handle token event
     * @return stream of new user token
     */
    override fun getTokenObservable(): Observable<UserToken> {
        return tokenEventSubject
            .flatMapSingle {
                Single.just(processTokenEvent(it))
            }
    }

    /**
     * Handle [TokenEvent] for determine token.
     * @param tokenEvent The event that we use to determine which token should be returned.
     */
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

    /**
     * We send event for refreshing session using the refresh token
     * @param refreshToken Token for updating session
     */
    override fun updateToken(refreshToken: String) {
        info("updateToken $refreshToken")
        if (refreshToken.isEmpty()) {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)
        tokenUpdatePublisher.accept(refreshToken)
    }

    /**
     * We send event for refreshing session using the [UserToken]
     * @param userToken for updating session
     */
    override fun updateToken(userToken: UserToken) {
        info("updateToken $userToken")
        if (userToken == UserToken.EMPTY_TOKEN || userToken.refreshToken == "") {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)
        tokenUpdatePublisher.accept(userToken.refreshToken)
    }

    /**
     * Enum class for determine state of current token
     */
    private enum class TokenEvent {
        /**
         * Used only when we are sure that we are using a valid token
         */
        TOKEN_VALID,

        /**
         * Used only when we are get error with message, that we have old token and we start to
         * process updating token
         */
        TOKEN_UPDATE,

        /**
         * Used only if we cannot to determine of token in storage
         */
        TOKEN_INVALID
    }
}
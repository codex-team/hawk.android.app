package so.codex.codexbl.providers

import android.util.Log
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import so.codex.core.UserTokenDAO
import so.codex.core.UserTokenProvider
import so.codex.core.entity.UserToken
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity

class UserTokenProviderImpl(
    userTokenDAO: UserTokenDAO,
    authApi: IAuthApi
) : UserTokenProvider {
    private val tokenEventSubject = BehaviorSubject.createDefault(TokenEvent.TOKEN_VALID)
    private val tokenUpdatePublisher = PublishSubject.create<String>()
    private val tokenSubject =
        BehaviorSubject.createDefault(userTokenDAO.getUserToken() ?: UserToken.EMPTY_TOKEN)

    init {
        tokenUpdatePublisher
            .filter {
                tokenEventSubject.hasValue() && tokenEventSubject.value!! == TokenEvent.TOKEN_UPDATE
            }
            .switchMapSingle {
                authApi.refreshToken(TokenEntity(it))
            }.subscribe({
                tokenSubject.onNext(UserToken(it.accessToken, it.refreshToken))
            }, {
                Log.e("UserTokenProvider", it.message, it)
            })
    }

    override fun getToken(): UserToken {
        val tokenEvent = tokenEventSubject.value ?: TokenEvent.TOKEN_INVALID
        return when (tokenEvent) {
            TokenEvent.TOKEN_INVALID -> {
                UserToken.EMPTY_TOKEN
            }
            TokenEvent.TOKEN_UPDATE -> {
                tokenSubject.blockingNext().take(1).first()
            }
            TokenEvent.TOKEN_VALID -> {
                tokenSubject.value ?: UserToken.EMPTY_TOKEN
            }
        }
    }

    override fun getTokenSingle(): Single<UserToken> {
        return tokenEventSubject
            .flatMapSingle {
                when (it) {
                    TokenEvent.TOKEN_INVALID -> {
                        Single.just(UserToken.EMPTY_TOKEN)
                    }
                    TokenEvent.TOKEN_UPDATE -> {
                        Single.just(tokenSubject.blockingNext().take(1).first())
                    }
                    TokenEvent.TOKEN_VALID -> {
                        Single.just(tokenSubject.value ?: UserToken.EMPTY_TOKEN)
                    }
                }
            }.single(UserToken.EMPTY_TOKEN)
    }

    override fun updateToken(refreshToken: String) {
        if (refreshToken.isEmpty()) {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)

    }

    override fun updateToken(userToken: UserToken) {
        if (userToken == UserToken.EMPTY_TOKEN || userToken.refreshToken == "") {
            return
        }
        tokenEventSubject.onNext(TokenEvent.TOKEN_UPDATE)
        tokenSubject.onNext(userToken)
    }

    private enum class TokenEvent {
        TOKEN_VALID,
        TOKEN_UPDATE,
        TOKEN_INVALID
    }

}
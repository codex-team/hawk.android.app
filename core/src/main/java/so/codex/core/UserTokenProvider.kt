package so.codex.core

import io.reactivex.Single
import so.codex.core.entity.UserToken

interface UserTokenProvider {
    fun getToken(): UserToken

    fun getTokenSingle(): Single<UserToken>

    fun updateToken(refreshToken: String)

    fun updateToken(userToken: UserToken)
}
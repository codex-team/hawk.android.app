package so.codex.core

import io.reactivex.Observable
import so.codex.core.entity.UserToken

interface UserTokenProvider {
    fun getToken(): UserToken

    fun getTokenSingle(): Observable<UserToken>

    fun updateToken(refreshToken: String)

    fun updateToken(userToken: UserToken)
}
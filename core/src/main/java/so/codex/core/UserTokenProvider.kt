package so.codex.core

import io.reactivex.Observable
import so.codex.core.entity.UserToken

/**
 * Interface that have common method for manipulation with user token
 */
interface UserTokenProvider {
    /**
     * Provide user token in that current moment
     * @return return user token [UserToken]
     */
    fun getToken(): UserToken

    /**
     * Provide stream of user tokens
     * @return return stream of user token
     */
    fun getTokenObservable(): Observable<UserToken>

    /**
     * Update session by refresh token
     * @param refreshToken refresh token for updating session
     */
    fun updateToken(refreshToken: String)

    /**
     * Update session by user token [UserToken]
     * @param userToken user token for updating session
     */
    fun updateToken(userToken: UserToken)
}
package so.codex.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.SingleTransformer
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


    /**
     * Method for refreshing token if it expired
     * @return [ObservableTransformer] that can handling if need to refresh token
     */
    fun <U> refreshToken(): ObservableTransformer<U, U>

    /**
     * Method for refreshing token if it expired
     * @return [SingleTransformer] that can handling if need to refresh token
     */
    fun <U> refreshTokenSingle(): SingleTransformer<U, U>
}
package so.codex.sourceinterfaces

import io.reactivex.Single
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Interface with declared method for sending http requests. The interface is only responsible for user authorization.
 * @author Shiplayer
 */
interface IAuthApi {
    /**
     * Send request to sign in application
     * @param auth Contain information that need for authorization
     * @return Return [Single] with [TokenResponse], that have response from server with access token and refresh token.
     */
    fun login(auth: AuthEntity): Single<TokenResponse>

    /**
     * Send request to sign up of new user
     * @param signUp Information for sign up of User
     * @return Return [Single] with boolean value, if user successfully sing up, then return true else false.
     */
    fun signUp(signUp: SignUpEntity): Single<Boolean>

    /**
     * Send request to update of session and get new access and refresh token
     * @param token Contain refresh token for updating session data
     * @return Return [Single] with [TokenResponse], that contain new information of access and refresh token
     */
    fun refreshToken(token: TokenEntity): Single<TokenResponse>
}
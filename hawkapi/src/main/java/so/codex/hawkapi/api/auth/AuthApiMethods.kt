package so.codex.hawkapi.api.auth

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Declared methods for sending request only for authorization
 * @author Shiplayer
 */
interface AuthApiMethods {
    /**
     * Send Post request for logging
     * @param authEntity Entity for authentication on server, representation as GraphQL request
     * @return response on login
     */
    @POST("/graphql")
    fun login(@Body authEntity: AuthEntity): Single<LoginResponse>

    /**
     * Send Post request for sign up
     * @param signUpEntity Entity for registration on server, representation as GraphQL request
     * @return response
     */
    @POST("/graphql")
    fun signUp(@Body signUpEntity: SignUpEntity): Single<SignUpResponse>

    /**
     * Send Post request for updating refresh token.
     * @param token Entity for updating token, representation as GraphQL request
     * @return response
     */
    @POST("/graphql")
    fun refreshToken(@Body token: TokenEntity): Single<TokenResponse>

    /**
     * Send Post request for updating refresh token.
     * @param token Entity for updating token, representation as GraphQL request
     * @return response
     */
    @POST("/graphql")
    fun refreshTokenObservable(@Body token: TokenEntity): Observable<TokenResponse>
}
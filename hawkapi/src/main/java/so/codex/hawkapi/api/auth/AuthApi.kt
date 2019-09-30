package so.codex.hawkapi.api.auth

import io.reactivex.Single
import so.codex.hawkapi.AuthApiMethodsImpl
import so.codex.hawkapi.api.CoreApi
import so.codex.hawkapi.api.TokenInterceptor
import so.codex.hawkapi.subscribeOnIO
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Implementation of interface [IAuthApi]. Class is singleton with provide method for sending request.
 * @author Shiplayer
 */
class AuthApi private constructor(private val service: AuthApiMethods) : IAuthApi {

    companion object {
        /**
         * Get instance of class
         */
        val instance by lazy {
            AuthApi(AuthApiMethodsImpl(CoreApi.apollo))
        }
    }

    /**
     * Send request to sign in
     * @param auth Information of authorization and converted to graphql request
     * @return Return response on request of authorization, contain access and refresh token
     */
    override fun login(auth: AuthEntity): Single<TokenResponse> =
            service.login(auth)
                .subscribeOnIO().map { it.login }

    /**
     * Send request to sign up of new user
     * @param signUp Information that need for registration of new user
     * @return Return response on request of registration, contain true or false if user will been registered
     */
    override fun signUp(signUp: SignUpEntity): Single<Boolean> =
        service.signUp(signUp)
            .subscribeOnIO().map { it.signUp }

    /**
     * Send request for updating session
     * @param token Refresh token for updating session
     * @return Return response on request of updating session with new information of session or error
     */
    override fun refreshToken(token: TokenEntity): Single<TokenResponse> =
        service.refreshToken(token)
            .subscribeOnIO()
            .doOnSuccess {
                TokenInterceptor.instance.updateToken(it.accessToken)
            }

}
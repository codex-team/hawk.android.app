package so.codex.hawkapi.api

import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import so.codex.core.UserTokenProvider
import java.util.concurrent.Executor

@Deprecated("Temporary not used for apollo")
class ApolloTokenInterceptor(val userTokenProvider: UserTokenProvider) : ApolloInterceptor {

    /*companion object {
        */
    /**
     * Representation as singleton and used for getting token
     *//*
        val instance = ApolloTokenInterceptor()
    }*/

    /**
     * Token that contain in instance.
     */
    private val token: String
        get() = userTokenProvider.getToken().accessToken

    private var disposable: Boolean = false

    /**
     * Update old token on new
     * @param token Token, that sending to server on every request
     */
    /*fun updateToken(token: String) {
        Log.i("TokenInterceptor", "token updated $token")
        this.token.set(token)
    }*/

    override fun interceptAsync(
        request: ApolloInterceptor.InterceptorRequest,
        chain: ApolloInterceptorChain,
        dispatcher: Executor,
        callBack: ApolloInterceptor.CallBack
    ) {
        val newRequest =
            if (!request.requestHeaders.hasHeader("Authorization") && token.isNotEmpty()) {
                val headers = request.requestHeaders.toBuilder().addHeader(
                    "Authorization", "Bearer $token"
                ).build()
                request.toBuilder().requestHeaders(headers).build()
            } else {
                request
            }
        if (!disposable)
            chain.proceedAsync(newRequest, dispatcher, callBack)
    }

    override fun dispose() {
        disposable = true
    }
}
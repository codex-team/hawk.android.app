package so.codex.hawkapi.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicReference

/**
 * Interceptor for changing old access token on new and valid token
 * @author Shiplayer
 */
class TokenInterceptor private constructor(): Interceptor {
    companion object {
        /**
         * Representation as singleton and used for getting token
         */
        val instance = TokenInterceptor()
    }

    /**
     * Token that contain in instance.
     */
    private var token = AtomicReference<String>("")

    /**
     * Update old token on new
     * @param token Token, that sending to server on every request
     */
    fun updateToken(token: String) {
        Log.i("TokenInterceptor", "token updated $token")
        this.token.set(token)
    }

    /**
     * Get token from [token] and add new header in request with token
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val localToken = token.get()
        val response = if (localToken.isNotEmpty()) {
            val request = origin.newBuilder()
                    .method(origin.method, origin.body)
                    .addHeader("Authorization", "Bearer $localToken")
                    .build()
            chain.proceed(
                    request
            )
        } else {
            chain.proceed(chain.request())
        }
        return response
    }

}
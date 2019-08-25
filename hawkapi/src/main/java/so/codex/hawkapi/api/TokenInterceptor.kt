package so.codex.hawkapi.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicReference

class TokenInterceptor : Interceptor {
    companion object {
        val instance = TokenInterceptor()
    }

    private var token = AtomicReference<String>("")

    fun updateToken(token: String) {
        Log.i("TokenInterceptor", "token updated $token")
        this.token.set(token)
    }

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
package so.codex.hawkapi.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    companion object {
        val instance = TokenInterceptor()
    }

    private var token: String = ""

    fun updateToken(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        return if (token.isNotEmpty())
            chain.proceed(
                origin.newBuilder()
                    .method(origin.method, origin.body)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        else
            chain.proceed(chain.request())
    }

}
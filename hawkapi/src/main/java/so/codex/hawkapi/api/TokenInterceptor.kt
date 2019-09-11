package so.codex.hawkapi.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.atomic.AtomicReference

/**
 * Перед тем, как отправить запрос на сервер, выполняется данный интерцептор, для замены старого
 * недействующего токена на новый.
 * @author Shiplayer
 */
class TokenInterceptor : Interceptor {
    companion object {
        val instance = TokenInterceptor()
    }

    /**
     * Токен, который находится в данный момент в интерцепторе
     */
    private var token = AtomicReference<String>("")

    /**
     * Обновить старый токен на новый
     * @param token Содержит новый токен, с котором будет отправляться запросы на сервер
     */
    fun updateToken(token: String) {
        Log.i("TokenInterceptor", "token updated $token")
        this.token.set(token)
    }

    /**
     * Перед отправлении запроса, получаем токен и добавляем заголовок для запроса
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
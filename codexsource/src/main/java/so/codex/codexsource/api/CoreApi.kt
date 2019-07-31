package so.codex.codexsource.api

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import so.codex.codexsource.SourceApi
import so.codex.sourceinterfaces.IAuthApi

/**
 * Ядро, в коротом определяются все API, с помощью которых можно будет взаимодействовать с сервером
 * [SourceApi] - интерфейс, в котором определены все необходимы API компоненты, которые отвечают за опреленные запросы
 */
class CoreApi private constructor() : SourceApi {
    companion object {
        private val baseUrl = "https://api.stage.hawk.so/"

        /**
         * Ленивая инициализация ретрофита для использования инициализации остальных компонентов для работы с API
         */
        val apollo: ApolloClient by lazy {
            ApolloClient.builder()
                    .serverUrl(baseUrl)
                    .build()
        }

        /**
         * Ленивая инициализация клиента
         */
        private val client by lazy {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        public val instance by lazy {
            CoreApi()
        }
    }

    /**
     * Метод, который возвращает API для взаимодействия с сервером во время авторизации
     * @return Возвращает интерфейс [IAuthApi], в котором есть все необходимые методы для авторизации
     * @see IAuthApi
     */
    override fun getAuthApi(): IAuthApi = AuthApi.instance
}
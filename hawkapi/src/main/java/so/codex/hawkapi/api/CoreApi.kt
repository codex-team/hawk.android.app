package so.codex.hawkapi.api

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import so.codex.hawkapi.SourceApi
import so.codex.hawkapi.api.auth.AuthApi
import so.codex.hawkapi.api.workspace.WorkspaceApi
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IWorkspaceApi

/**
 * Ядро, в коротом определяются все API, с помощью которых можно будет взаимодействовать с сервером
 * [SourceApi] - интерфейс, в котором определены все необходимы API компоненты, которые отвечают за опреленные запросы
 */
class CoreApi private constructor() : SourceApi {
    companion object {
        private val baseUrl = "https://api.stage.hawk.so/graphql"

        /**
         * Ленивая инициализация ретрофита для использования инициализации остальных компонентов для работы с API
         */
        val apollo: ApolloClient by lazy {
            ApolloClient.builder()
                    .serverUrl(baseUrl)
                    .okHttpClient(client)
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

        val instance by lazy {
            CoreApi()
        }
    }

    /**
     * Метод, который возвращает API для взаимодействия с сервером во время авторизации
     * @return Возвращает интерфейс [IAuthApi], в котором есть все необходимые методы для авторизации
     * @see IAuthApi
     */
    override fun getAuthApi(): IAuthApi = AuthApi.instance

    override fun getWorkspaceApi(): IWorkspaceApi = WorkspaceApi.instance
}
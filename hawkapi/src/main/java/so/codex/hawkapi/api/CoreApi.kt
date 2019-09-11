package so.codex.hawkapi.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import so.codex.hawkapi.SourceApi
import so.codex.hawkapi.api.auth.AuthApi
import so.codex.hawkapi.api.workspace.WorkspaceApi
import so.codex.hawkapi.type.CustomType
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IWorkspaceApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Ядро, в коротом определяются все API, с помощью которых можно будет взаимодействовать с сервером
 * [SourceApi] - интерфейс, в котором определены все необходимы API компоненты, которые отвечают за опреленные запросы
 * @author Shiplayer
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
                    .addCustomTypeAdapter(CustomType.DATETIME, customDateTimeAdapter)
                    .build()
        }

        /**
         * Паттерн времени, по которму можно будет сконвертировать в Long
         */
        private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        /**
         * Реализация конвертирования Date из GraphQL в джавовский Date
         */
        private val customDateTimeAdapter = object : CustomTypeAdapter<Date> {
            override fun encode(value: Date): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(dateFormat.format(value))
            }

            override fun decode(value: CustomTypeValue<*>): Date {
                return dateFormat.parse(value.value.toString())
            }
        }

        /**
         * Ленивая инициализация клиента
         */
        private val client by lazy {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            OkHttpClient.Builder()
                    .addInterceptor(TokenInterceptor.instance)
                    .addInterceptor(interceptor)
                    .build()
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

    /**
     * Метод, который возвращает API для взаимодействия с сервером для получения информации о Workspace
     * @return Возвращает интерфейс [IWorkspaceApi], в котором есть все необходимые методы для
     * получения информации о Workspace
     * @see IWorkspaceApi
     */
    override fun getWorkspaceApi(): IWorkspaceApi = WorkspaceApi.instance
}
package so.codex.hawkapi.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import so.codex.hawkapi.SourceApi
import so.codex.hawkapi.api.auth.AuthApi
import so.codex.hawkapi.api.workspace.WorkspaceApi
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IWorkspaceApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Main class of API that can provide interfaces api for communication with server, sending and getting information.
 * [SourceApi] - interface that declared method for providing api for communication with server.
 * @author Shiplayer
 */
class CoreApi private constructor() : SourceApi {
    companion object {
        private val baseUrl = "https://api.stage.hawk.so/graphql"

        /**
         * Lazy initializing apollo.
         */
        val apollo: ApolloClient by lazy {
            ApolloClient.builder()
                .serverUrl(baseUrl)
                .okHttpClient(client)
                .build()
        }

        /**
         * For converting String to Long
         */
        private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        /**
         * Converting Date from GrapghQL to Data from Java
         */
        private val customDateTimeAdapter = object : CustomTypeAdapter<Date> {
            override fun encode(value: Date): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(dateFormat.format(value))
            }

            override fun decode(value: CustomTypeValue<*>): Date {
                val dateValue = value.value.toString().toFloatOrNull()
                return if(dateValue != null)
                    Date((dateValue * 1000).toLong())
                else
                    dateFormat.parse(value.value.toString())
            }
        }

        /**
         * Lazy initializing of client with http logger and token interceptor.
         * @see TokenInterceptor
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

        /**
         * Singleton of CoreApi
         */
        val instance by lazy {
            CoreApi()
        }
    }

    /**
     * Provide interface for working with Authorization.
     * @return Interface [IAuthApi], that implemented methods for sending request.
     * @see IAuthApi
     */
    override fun getAuthApi(): IAuthApi = AuthApi.instance

    /**
     * Provide interface for working with Workspaces.
     * @return Interface [IWorkspaceApi], that implemented methods for sending request.
     * @see IWorkspaceApi
     */
    override fun getWorkspaceApi(): IWorkspaceApi = WorkspaceApi.instance
}
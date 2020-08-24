package so.codex.hawkapi.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.hawkapi.SourceApi
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IProfileApi
import so.codex.sourceinterfaces.IWorkspaceApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Main class of API that can provide interfaces api for communication with server, sending and getting information.
 * [SourceApi] - interface that declared method for providing api for communication with server.
 * @author Shiplayer
 * @author YorkIsMine
 */
class CoreApi private constructor() : SourceApi, KoinComponent {
    companion object {
        private const val baseUrl = "https://api.stage.hawk.so/graphql"

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
         * Singleton of CoreApi
         */
        val instance by lazy {
            CoreApi()
        }
    }

    /**
     * Lazy initializing of client with http logger and token interceptor.
     * @see TokenInterceptor
     */
    private val client: OkHttpClient by inject()


    /**
     * Lazy initializing apollo.
     */
    val apollo: ApolloClient by inject()

    /**
     * Provide interface for working with Authorization.
     * @return Interface [IAuthApi], that implemented methods for sending request.
     * @see IAuthApi
     */
//    override fun getAuthApi(): IAuthApi = AuthApi.instance

    /**
     * Provide interface for working with Workspaces.
     * @return Interface [IWorkspaceApi], that implemented methods for sending request.
     * @see IWorkspaceApi
     */
//    override fun getWorkspaceApi(): IWorkspaceApi = WorkspaceApi.instance

    /**
     * Provide interface for working with Profile.
     * @return Interface [IProfileApi], that implemented methods for sending request.
     * @see IProfileApi
     */
//    override fun getProfileApi(): IProfileApi = ProfileApi.instance
}
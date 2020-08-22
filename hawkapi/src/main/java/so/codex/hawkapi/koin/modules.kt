package so.codex.hawkapi.koin

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import so.codex.hawkapi.AuthApiMethodsImpl
import so.codex.hawkapi.ProfileApiMethodsImpl
import so.codex.hawkapi.WorkspaceApiMethodImpl
import so.codex.hawkapi.api.ApolloTokenInterceptor
import so.codex.hawkapi.api.auth.AuthApi
import so.codex.hawkapi.api.profile.ProfileApi
import so.codex.hawkapi.api.workspace.WorkspaceApi
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IProfileApi
import so.codex.sourceinterfaces.IWorkspaceApi

private const val baseUrl = "https://api.stage.hawk.so/graphql"

/**
 * Modules for dependencies API classes and getting necessary information
 */
val coreNetworkModule = module {
    single { ApolloTokenInterceptor(get()) }
    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    single<ApolloClient> {
        ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(get())
            .build()
    }
}

/**
 * Here describe all dependencies for dependency injection
 * @author Shiplayer
 */
val authApiModule = module {
    single { AuthApiMethodsImpl(get()) }
    single<IAuthApi> { AuthApi(get()) }

}

/**
 * Modules for dependencies API classes and getting necessary information
 */
val networkModule = module {
    single { WorkspaceApiMethodImpl(get()) }
    single<IWorkspaceApi> { WorkspaceApi(get(), get()) }
    single { ProfileApiMethodsImpl(get()) }
    single<IProfileApi> { ProfileApi(get()) }
}
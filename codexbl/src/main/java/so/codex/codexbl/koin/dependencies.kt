package so.codex.codexbl.koin

import org.koin.dsl.module
import so.codex.codexbl.interactors.*
import so.codex.codexbl.providers.UserTokenProviderImpl
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.api.CoreApi

/**
 * Here describe all dependencies for dependency injection
 * @author Shiplayer
 */

val authApiModule = module {
    single { CoreApi.instance.getAuthApi() }
}

val authInteractorsModule = module {
    factory<ISignInInteractor> { SignInInteractor() }
    factory<ISignUpInteractor> { SignUpInteractor() }
    factory<IUserInteractor> { UserInteractor() }
}

/**
 * Modules for dependencies API classes and getting necessary information
 */
val apiModule = module {
    single { CoreApi.instance.getWorkspaceApi() }
}

/**
 * All interactors for communication
 */
val interactorsModule = module {
    factory<IWorkspaceInteractor> { WorkspaceInteractor() }
    single { RefreshInteractor() }
}

val mainActivityInteractorsModule = module {

}

/**
 * Provide storage or other providers
 * Предоставляют какое-то хранилище для какой-то информации
 */
val providersModule = module {
    single<UserTokenProvider> { UserTokenProviderImpl(get(), get()) }
}
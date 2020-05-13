package so.codex.codexbl.koin

import org.koin.dsl.module
import so.codex.codexbl.interactors.*
import so.codex.hawkapi.api.CoreApi

/**
 * Here describe all dependencies for dependency injection
 * @author Shiplayer
 */

/**
 * Modules for dependencies API classes and getting necessary information
 */
val apiModule = module {
    single { CoreApi.instance.getAuthApi() }
    single { CoreApi.instance.getWorkspaceApi() }
}

/**
 * All interactors for communication
 */
val interactorsModule = module {
    factory<ISignInInteractor> { SignInInteractor() }
    factory<ISignUpInteractor> { SignUpInteractor() }
    factory<IUserInteractor> { UserInteractor() }
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
}
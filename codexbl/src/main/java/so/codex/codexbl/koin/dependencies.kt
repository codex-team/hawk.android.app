package so.codex.codexbl.koin

import org.koin.dsl.module
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.interactors.ISignUpInteractor
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.interactors.IUserInteractor
import so.codex.codexbl.interactors.UserInteractor
import so.codex.codexbl.interactors.IWorkspaceInteractor
import so.codex.codexbl.interactors.WorkspaceInteractor
import so.codex.codexbl.interactors.IProfileInteractor
import so.codex.codexbl.interactors.ProfileInteractor
import so.codex.codexbl.providers.UserTokenProviderImpl
import so.codex.codexbl.providers.UserTokenDAO
import so.codex.codexbl.providers.UserTokenPreferences
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
    factory<IProfileInteractor> { ProfileInteractor() }
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
package so.codex.codexbl.koin

import org.koin.core.qualifier.named
import org.koin.dsl.module
import so.codex.codexbl.interactors.*
import so.codex.codexbl.providers.UserTokenDAO
import so.codex.codexbl.providers.UserTokenPreferences
import so.codex.codexbl.providers.UserTokenProvider
import so.codex.hawkapi.api.CoreApi

val apiModule = module {
    single { CoreApi.instance.getAuthApi() }
    single { CoreApi.instance.getWorkspaceApi() }
}

val interactorsModule = module {
    factory<ISignInInteractor> { SignInInteractor() }
    factory<ISignUpInteractor> { SignUpInteractor() }
    factory<IUserInteractor> { UserInteractor() }
    factory<IWorkspaceInteractor> { WorkspaceInteractor() }
}

val providersModule = module {
    single { UserTokenProvider() }
    single<UserTokenDAO>(named("preferences")) { UserTokenPreferences(get()) }
}
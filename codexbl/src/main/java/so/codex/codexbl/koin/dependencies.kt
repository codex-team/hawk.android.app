package so.codex.codexbl.koin

import org.koin.core.qualifier.named
import org.koin.dsl.module
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.interactors.ISignUpInteractor
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.providers.UserTokenDAO
import so.codex.codexbl.providers.UserTokenPreferences
import so.codex.codexbl.providers.UserTokenProvider
import so.codex.codexsource.api.CoreApi

val apiModule = module {
    single { CoreApi.instance.getAuthApi() }
}

val interactorsModule = module {
    factory<ISignInInteractor> { SignInInteractor() }
    single<ISignUpInteractor> { SignUpInteractor() }
}

val providersModule = module {
    single { UserTokenProvider() }
    single<UserTokenDAO>(named("preferences")) { UserTokenPreferences(get()) }
}
package so.codex.codexbl.koin

import org.koin.core.qualifier.named
import org.koin.dsl.module
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.presenter.SignInPresenter
import so.codex.codexbl.providers.UserTokenDAO
import so.codex.codexbl.providers.UserTokenPreferences
import so.codex.codexbl.providers.UserTokenProvider

val apiModule = module {
    single {  }
}

val interactorsModule = module {
    factory<ISignInInteractor> { SignInInteractor() }
}

val providersModule = module {
    single { UserTokenProvider() }
    single<UserTokenDAO>(named("preferences")) { UserTokenPreferences(get()) }
}
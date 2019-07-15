package so.codex.codexbl.koin

import org.koin.dsl.module
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.providers.UserTokenProvider

val apiModule = module {

}

val interactorsModule = module {
    factory { SignInInteractor() }
}

val providersModule = module {
    single { UserTokenProvider() }
}
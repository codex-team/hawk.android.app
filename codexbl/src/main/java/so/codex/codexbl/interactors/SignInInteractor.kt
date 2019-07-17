package so.codex.codexbl.interactors

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.providers.UserTokenDAO

class SignInInteractor : ISignInInteractor, KoinComponent {

    val userTokenDAO: UserTokenDAO by inject(named("preferences"))


}
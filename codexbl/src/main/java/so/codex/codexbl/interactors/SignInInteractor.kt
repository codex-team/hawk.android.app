package so.codex.codexbl.interactors

import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.UserAuth
import so.codex.codexbl.providers.UserTokenDAO

class SignInInteractor : ISignInInteractor, KoinComponent {
    val userTokenDAO: UserTokenDAO by inject(named("preferences"))

    override fun signIn(userAuth: UserAuth): Single<Boolean> {

    }

}
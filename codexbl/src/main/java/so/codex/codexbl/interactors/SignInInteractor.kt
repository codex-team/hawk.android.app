package so.codex.codexbl.interactors

import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import so.codex.codexbl.entity.UserAuth
import so.codex.codexbl.entity.UserToken
import so.codex.codexbl.providers.UserTokenDAO
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.entity.AuthEntity

class SignInInteractor : ISignInInteractor, KoinComponent { 

    override fun signIn(userAuth: UserAuth): Single<Boolean> {
        return CoreApi.instance.getAuthApi().login(AuthEntity(userAuth.email, userAuth.password)).doOnSuccess {

        }.map {

        }/*.onErrorResumeNext {
            it.printStackTrace()
            Single.just(false)
        }*/
    }

}
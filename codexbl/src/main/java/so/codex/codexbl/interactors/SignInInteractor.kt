package so.codex.codexbl.interactors

import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserAuth
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.entity.AuthEntity

class SignInInteractor : ISignInInteractor, KoinComponent {
    private val userInteractor by inject<IUserInteractor>()

    override fun signIn(userAuth: UserAuth): Single<Boolean> {
        return CoreApi.instance.getAuthApi().login(AuthEntity(userAuth.email, userAuth.password)).doOnSuccess {

        }.map {
            userInteractor.saveSession(SessionData(userAuth.email, it.accessToken, it.refreshToken))
        }/*.onErrorResumeNext {
            it.printStackTrace()
            Single.just(false)
        }*/
    }

}
package so.codex.codexbl.interactors

import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.SessionData
import so.codex.codexbl.entity.UserAuth
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.entity.AuthEntity

/**
 * The Interactor with implementation methods from [ISignInInteractor] for communication with API.
 * @author Shiplayer
 */
class SignInInteractor : ISignInInteractor, KoinComponent {
    /**
     * Used for getting and saving session
     */
    private val userInteractor by inject<IUserInteractor>()

    /**
     * Send request to login and save session
     */
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
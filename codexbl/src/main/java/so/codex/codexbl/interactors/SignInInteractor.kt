package so.codex.codexbl.interactors

import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.entity.UserAuth
import so.codex.core.entity.SessionData
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.AuthEntity

/**
 * The Interactor with implementation methods from [ISignInInteractor] for communication with API.
 * @author Shiplayer
 */
class SignInInteractor(private val authApi: IAuthApi) : ISignInInteractor, KoinComponent {
    /**
     * Used for getting and saving session
     */
    private val userInteractor by inject<IUserInteractor>()

    /**
     * Send request to login and save session
     */
    override fun signIn(userAuth: UserAuth): Single<Boolean> {
        return authApi.login(AuthEntity(userAuth.email, userAuth.password)).doOnSuccess {

        }.map {
            userInteractor.saveSession(
                SessionData(
                    userAuth.email,
                    it.accessToken,
                    it.refreshToken
                )
            )
        }/*.onErrorResumeNext {
            it.printStackTrace()
            Single.just(false)
        }*/
    }

}
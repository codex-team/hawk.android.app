package so.codex.codexbl.interactors

import io.reactivex.rxjava3.core.Single
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.SignUpEntity

/**
 * The Interactor with implementation methods from [ISignUpInteractor] for communication with API.
 * @param authApi Used for sending authorization information for sign up in system
 * @author Shiplayer
 */
class SignUpInteractor(private val authApi: IAuthApi) : ISignUpInteractor {
    /**
     * Send request to sign up of new user
     */
    override fun signUp(email: String): Single<Boolean> {
        return authApi.signUp(SignUpEntity(email))
    }

}
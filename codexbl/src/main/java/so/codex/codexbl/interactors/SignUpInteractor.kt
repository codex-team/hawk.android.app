package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.entity.SignUpEntity

/**
 * The Interactor with implementation methods from [ISignUpInteractor] for communication with API.
 * @author Shiplayer
 */
class SignUpInteractor : ISignUpInteractor {
    /**
     * Send request to sign up of new user
     */
    override fun signUp(email: String): Single<Boolean> {
        return CoreApi.instance.getAuthApi().signUp(SignUpEntity(email))
    }

}
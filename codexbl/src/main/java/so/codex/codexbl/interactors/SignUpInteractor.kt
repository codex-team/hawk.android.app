package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.entity.SignUpEntity

/**
 * Интерактор, в котором реализованы методы из интерфейса [ISignUpInteractor], взаимодействие с API
 * @author Shiplayer
 */
class SignUpInteractor : ISignUpInteractor {
    override fun signUp(email: String): Single<Boolean> {
        return CoreApi.instance.getAuthApi().signUp(SignUpEntity(email))
    }

}
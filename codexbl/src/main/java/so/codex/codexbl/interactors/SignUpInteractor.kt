package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.codexsource.api.CoreApi
import so.codex.sourceinterfaces.entity.SignUpEntity

class SignUpInteractor : ISignUpInteractor {
    override fun signUp(email: String): Single<Boolean> {
        return CoreApi.instance.getAuthApi().signUp(SignUpEntity(email))
    }

}
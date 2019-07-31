package so.codex.codexbl.interactors

import io.reactivex.Single

interface ISignUpInteractor {
    fun signUp(email: String): Single<Boolean>
}
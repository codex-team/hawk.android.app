package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.codexbl.entity.UserAuth

interface ISignInInteractor {
    fun signIn(userAuth: UserAuth): Single<Boolean>
}
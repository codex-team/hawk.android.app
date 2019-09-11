package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.codexbl.entity.UserAuth

/**
 * Интерфейс, в котором определены методы необходимые для входа
 * @author Shiplayer
 */
interface ISignInInteractor {
    fun signIn(userAuth: UserAuth): Single<Boolean>
}
package so.codex.codexbl.interactors

import io.reactivex.rxjava3.core.Single
import so.codex.codexbl.entity.UserAuth

/**
 * Interface with declared methods for sign in
 * @author Shiplayer
 */
interface ISignInInteractor {
    /**
     * Sign in of user
     * @param userAuth User authentication representation
     * @return [Single] with boolean, if user authentication return true else false
     */
    fun signIn(userAuth: UserAuth): Single<Boolean>
}
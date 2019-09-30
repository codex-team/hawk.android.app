package so.codex.codexbl.interactors

import io.reactivex.Single

/**
 * Interface with declared methods for sign up
 * @author Shiplayer
 */
interface ISignUpInteractor {
    /**
     * Sign up of new user
     * @param email Email representation
     * @return [Single] With boolean, if user successful registered return true else false
     */
    fun signUp(email: String): Single<Boolean>
}
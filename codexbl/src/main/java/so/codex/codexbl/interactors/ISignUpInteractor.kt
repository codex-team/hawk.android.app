package so.codex.codexbl.interactors

import io.reactivex.Single

/**
 * Интерфейс, в котором определены методы необходимые для регистрации
 * @author Shiplayer
 */
interface ISignUpInteractor {
    fun signUp(email: String): Single<Boolean>
}
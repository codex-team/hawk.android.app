package so.codex.codexbl.interactors.interfaces

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Интерфейс с методами, необходимыми для получения токена и, если он не действительный,
 * обновления токена
 * @author Shiplayer
 */
interface IRefreshableInteractor {
    /**
     * Получение токена из хранилища
     */
    val token: String
    //fun getToken(): String

    /**
     * Метод, с помощью которого будет обновлен токен, если он устарел
     */
    fun <T> Observable<T>.refreshToken(): Observable<T>

    /**
     * Метод, с помощью которого будет обновлен токен, если он устарел
     */
    fun <T> Single<T>.refreshTokenSingle(): Single<T>
}
package so.codex.codexbl.interactors.interfaces

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface with declared methods for getting or auto refreshing token
 * @author Shiplayer
 */
interface IRefreshableInteractor {
    /**
     * Getting token
     */
    val token: String
    //fun getToken(): String

    /**
     * Method for refreshing token if it expired
     */
    fun <T> Observable<T>.refreshToken(): Observable<T>

    /**
     * Method for refreshing token if it expired
     */
    fun <T> Single<T>.refreshTokenSingle(): Single<T>
}
package so.codex.codexbl.interactors.interfaces

import io.reactivex.Observable

interface IRefreshableInteractor {
    fun getToken(): String
    fun <T> Observable<T>.refreshToken(): Observable<T>
    fun <T> Observable<T>.refreshTokenSingle(): Observable<T>
}
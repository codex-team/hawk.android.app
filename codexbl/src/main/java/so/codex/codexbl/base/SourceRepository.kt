package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable

interface SourceRepository<out T> {
    fun refresh()

    fun getObservable(): Observable<out T>
}
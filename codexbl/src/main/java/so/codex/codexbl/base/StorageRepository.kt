package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable

interface StorageRepository<T> {

    fun save(data: T)

    fun load(): T?

    fun getObservable(): Observable<T>
}
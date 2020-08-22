package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import so.codex.core.Optional

class CacheRepository<T>(defaultValue: T? = null) : StorageRepository<T> {
    private val subject = BehaviorSubject.createDefault<Optional<T>>(
        if (defaultValue == null) Optional.nil() else Optional.of(defaultValue)
    )

    override fun save(data: T) {
        subject.onNext(Optional.of(data))
    }

    override fun load(): T? {
        val value = subject.value
        return if (value == null || value.isNotPresent) {
            null
        } else {
            value.get()
        }
    }

    override fun getObservable(): Observable<T> {
        return subject.hide().filter {
            it.isPresent
        }.map(Optional<T>::get)
    }
}
package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable

/**
 * Interface with common methods that need for implementation of source
 */
interface SourceRepository<out T> {
    /**
     * Request refresh
     */
    fun refresh()

    /**
     * Get stream of data from source
     * @return Stream of data
     */
    fun getObservable(): Observable<out T>
}
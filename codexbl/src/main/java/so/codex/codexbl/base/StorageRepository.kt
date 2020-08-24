package so.codex.codexbl.base

import io.reactivex.rxjava3.core.Observable

/**
 * Interface that representation common methods that need any storage classes
 */
interface StorageRepository<T> {

    /**
     * Save data in storage
     * @param data Data that need to save in storage
     */
    fun save(data: T)

    /**
     * Get data from storage
     * @return Return data that contains in storage
     */
    fun load(): T?

    /**
     * Get stream of all changes in storage
     * @return Stream of data
     */
    fun getObservable(): Observable<T>

    /**
     * Clear all data that contains in sotrage
     */
    fun clear()
}
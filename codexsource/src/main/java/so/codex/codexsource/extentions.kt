package so.codex.codexsource

import com.apollographql.apollo.api.Response
import io.reactivex.Observable
import io.reactivex.Single
import so.codex.codexsource.exceptions.MultiHttpErrorsException
import so.codex.codexsource.exceptions.SomethingWentWrongException

fun <T: Response<O?>, O: Any?> Observable<T?>.handleHttpErrorsSingle() : Single<O> {
    return firstOrError().flatMap {
        when {
            it.hasErrors() -> {
                val errors = it.errors()
                Single.error<O>(MultiHttpErrorsException("Multi errors", errors.map { it.message() ?: "" }))
            }
            it.data() != null -> Single.just(it.data()!!)
            it.data() == null -> Single.error<O>(Throwable("login data is null"))
            else -> Single.error<O>(SomethingWentWrongException())
        }
    }
}
fun <T: Response<*>> Observable<T?>.handleHttpErrors() : Observable<T> {
    return flatMap {
        when {
            it.data() == null -> Observable.error<T>(Throwable("login data is null"))
            it.hasErrors() -> {
                val errors = it.errors()
                Observable.error<T>(MultiHttpErrorsException("Multi errors", errors.map { it.message() ?: "" }))
            }
            else -> Observable.error<T>(SomethingWentWrongException())
        }
    }
}
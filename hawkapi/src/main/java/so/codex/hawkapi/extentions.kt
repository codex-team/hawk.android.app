package so.codex.hawkapi

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.hawkapi.exceptions.BaseHttpException
import so.codex.hawkapi.exceptions.MultiHttpErrorsException
import so.codex.hawkapi.exceptions.SomethingWentWrongException

fun <T: Response<O?>, O: Any?> Observable<T?>.handleHttpErrorsSingle() : Single<O> {
    return firstOrError().flatMap {
        when {
            it.hasErrors() -> {
                val errors = it.errors()
                if (errors.size > 1)
                    Single.error<O>(MultiHttpErrorsException("Multi errors", errors.map { it.message() ?: "" }))
                else
                    Single.error<O>(errors[0]!!.convert())
            }
            it.data() != null -> Single.just(it.data()!!)
            it.data() == null -> Single.error<O>(Throwable("login data is null"))
            else -> Single.error<O>(SomethingWentWrongException())
        }
    }
}

fun <T : Response<O?>, O : Any?> Observable<T?>.handleHttpErrors(): Observable<O> {
    return flatMap {
        when {
            it.data() == null -> Observable.error<O>(Throwable("login data is null"))
            it.data() != null -> Observable.just(it.data()!!)
            it.hasErrors() -> {
                val errors = it.errors()
                if (errors.size > 1)
                    Observable.error<O>(MultiHttpErrorsException("Multi errors", errors.map { it.message() ?: "" }))
                else
                    Observable.error<O>(errors[0]!!.convert())
            }
            else -> Observable.error<O>(SomethingWentWrongException())
        }
    }
}

private fun Error.convert(): BaseHttpException =
    customAttributes().let {
        return if (it.containsKey("extensions")) {
            when ((it["extensions"] as LinkedHashMap<String, *>)["code"] as String) {
                "ACCESS_TOKEN_EXPIRED_ERROR" -> AccessTokenExpiredException()
                else -> BaseHttpException(this.message())
            }
        } else
            BaseHttpException(this.message())
    }

fun <T> Observable<T>.subscribeOnIO(): Observable<T> = subscribeOn(Schedulers.io())
fun <T> Single<T>.subscribeOnIO(): Single<T> = subscribeOn(Schedulers.io())
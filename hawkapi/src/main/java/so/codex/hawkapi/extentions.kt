package so.codex.hawkapi

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import so.codex.hawkapi.exceptions.AccessTokenExpiredException
import so.codex.hawkapi.exceptions.BaseHttpException
import so.codex.hawkapi.exceptions.MultiHttpErrorsException
import so.codex.hawkapi.exceptions.SomethingWentWrongException

/**
 * Здесь определены основные расширения некоторых классов.
 * @author Shiplayer
 */

/**
 * Расширение [Observable] для обработки ошибок, которые могут возникнуть во время отправки запроса
 * @return Возвращает уже [Single] с уже раскрытым объектом от [Response] в его тип, который он
 * содержит.
 */
fun <T : Response<O>, O : Any?> Observable<T>.handleHttpErrorsSingle(): Single<O> {
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
            it.data() == null -> Single.error<O>(Throwable("Data is null"))
            else -> Single.error<O>(SomethingWentWrongException())
        }
    }
}

/**
 * Расширение [Observable] для обработки ошибок, которые могут возникнуть во время отправки запроса
 * @return Возвращает [Observable] с уже раскрытым объектом от [Response] в его тип, который он
 * содержит.
 */
fun <T : Response<O>, O : Any?> Observable<T?>.handleHttpErrors(): Observable<O> {
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

/**
 * Конвертирует [ApolloCall] в Rx поток, который можно будет еще раз вызвать, если произошла ошибка
 * и нужно будет отправить запрос заново.
 * @return Возвращает [Observable] аналогичный [ApolloCall]
 */
fun <T> ApolloCall<T>.toRxJava(): Observable<Response<T>> {
    return Observable.create<Response<T>> {
        val call = clone()
        call.enqueue(object : ApolloCall.Callback<T>() {
            override fun onFailure(e: ApolloException) {
                it.onError(e)
            }

            override fun onResponse(response: Response<T>) {
                it.onNext(response)
            }

        })
    }
}

/**
 * Конвертирует [ApolloCall] в Rx поток, который можно будет еще раз вызвать, если произошла ошибка
 * и нужно будет отправить запрос заново.
 * @param before лямба выражение, которое выполняется во время конвертации для выполнения каких либо
 * действий
 * @return Возвращает [Observable] аналогичный [ApolloCall]
 */
fun <T> ApolloCall<T>.toRxJava(before: () -> Unit): Observable<Response<T>> {
    return Observable.create<Response<T>> {
        before()
        val call = clone()
        call.enqueue(object : ApolloCall.Callback<T>() {
            override fun onFailure(e: ApolloException) {
                it.onError(e)
            }

            override fun onResponse(response: Response<T>) {
                it.onNext(response)
            }

        })
    }
}

/**
 * Конвертирует ошибку из GraphQL [Error] в [BaseHttpException]
 */
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
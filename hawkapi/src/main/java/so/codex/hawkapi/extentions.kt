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
 * In here declared common extensions for working RxJava and ApolloCalls.
 * @author Shiplayer
 */

/**
 * Extension [Observable] for handing errors, that make occurred in while request or on the server.
 * @return [Single] with unwrapped instance in [Response] to type that it contains
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
 * Extension [Observable] for handing errors, that make occurred in while request or on the server.
 * @return [Observable] with unwrapped instance in [Response] to type that it contains
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
 * Convert [ApolloCall] to [Observable], that may call again if error occurred and send request again
 * @return [Observable] similar [ApolloCall]
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
 * Convert [ApolloCall] to [Observable], that may call again if error occurred and send request again
 * @param before lambda, that need invoke before sending request
 * @return [Observable] similar [ApolloCall]
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
 * Convert error of GraphQL [Error] to [BaseHttpException]
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

/**
 * Work in other thread for IO communication
 */
fun <T> Observable<T>.subscribeOnIO(): Observable<T> = subscribeOn(Schedulers.io())

/**
 * Work in other thread for IO communication
 */
fun <T> Single<T>.subscribeOnIO(): Single<T> = subscribeOn(Schedulers.io())
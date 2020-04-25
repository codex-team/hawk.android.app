package so.codex.hawkapi

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloMutationCall
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.rx2.rxMutate
import com.apollographql.apollo.rx2.rxQuery
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
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
 * Convert error of GraphQL [Error] to [BaseHttpException]
 */

fun Error.convert(): BaseHttpException =
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
 * Creates a new [ApolloQueryCall] call and then converts it to an [Observable]. If [ApolloQueryCall]
 * was canceled then we can call retry in rx chains.
 *
 * The number of emissions this Observable will have is based on the
 * [com.apollographql.apollo.fetcher.ResponseFetcher] used with the call.
 */
@JvmSynthetic
@CheckReturnValue
fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.retryQuery(
    query: Query<D, T, V>
): Observable<Response<T>> = Observable.just(query).flatMap { rxQuery(it) { this.clone() } }

/**
 * Creates a new [ApolloMutationCall] call and then converts it to a [Single]. If [ApolloQueryCall]
 * was canceled then we can call retry in rx chains.
 */
@JvmSynthetic
@CheckReturnValue
fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.retryMutate(
    mutation: Mutation<D, T, V>
): Single<Response<T>> = Single.just(mutation).flatMap { rxMutate(it) { this.clone() } }

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
                    Single.error<O>(
                        MultiHttpErrorsException(
                            "Multi errors",
                            errors.map { it.message() ?: "" })
                    )
                else
                    Single.error<O>(errors[0].convert())
            }
            it.data() != null -> Single.just(it.data()!!)
            it.data() == null -> Single.error<O>(Throwable("Data is null"))
            else -> Single.error<O>(SomethingWentWrongException())
        }
    }
}

/**
 * Extension [Single] for handing errors, that make occurred in while request or on the server.
 * @return [Single] with unwrapped instance in [Response] to type that it contains
 */
fun <T : Response<O>, O : Any?> Single<T>.handleHttpErrors(): Single<O> {
    return flatMap {
        when {
            it.hasErrors() -> {
                val errors = it.errors()
                if (errors.size > 1)
                    Single.error<O>(
                        MultiHttpErrorsException(
                            "Multi errors",
                            errors.map { it.message() ?: "" })
                    )
                else
                    Single.error<O>(errors[0].convert())
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
                    Observable.error<O>(
                        MultiHttpErrorsException(
                            "Multi errors",
                            errors.map { it.message() ?: "" })
                    )
                else
                    Observable.error<O>(errors[0].convert())
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
 * Work in other thread for IO communication
 */
fun <T> Observable<T>.subscribeOnIO(): Observable<T> = subscribeOn(Schedulers.io())

/**
 * Work in other thread for IO communication
 */
fun <T> Single<T>.subscribeOnIO(): Single<T> = subscribeOn(Schedulers.io())
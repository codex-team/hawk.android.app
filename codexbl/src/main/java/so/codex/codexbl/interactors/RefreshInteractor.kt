package so.codex.codexbl.interactors

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.core.entity.UserToken
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class RefreshInteractor : KoinComponent {
    companion object {
        val scheduler = Executors.newFixedThreadPool(1)!!
    }


    private val subject = PublishSubject.create<UserToken>()

    private val inProgress = AtomicBoolean(false)

    private val authApi: IAuthApi by inject()

    private val tokenSubject = BehaviorSubject.create<UserToken>()

    private val tokenResponseObservable = tokenSubject.flatMap {
        authApi.refreshToken(TokenEntity(it.refreshToken))
            .doAfterSuccess {
                inProgress.set(false)
            }.toObservable()
    }

    private val tokenObserver = subject
        .share()
        .subscribeOn(Schedulers.single())
        .distinctUntilChanged() { e1, e2 ->
            info("compare e1 = $e1")
            info("compare e2 = $e2")
            info("compare to tokens e1.refreshToken == e2.refreshToken = ${e1.refreshToken == e2.refreshToken}")
            e1.refreshToken == e2.refreshToken
        }
        .doOnSubscribe {
            info("subscribe")
        }.flatMap {
            info("refreshToken threadName = ${Thread.currentThread().id}")
            tokenSubject.onNext(it)
            tokenResponseObservable
        }


    init {
//        subject.subscribe()
        //tokenObserver.subscribe()
    }

    fun refreshToken(token: UserToken): Observable<TokenResponse> {
        Thread.currentThread().stackTrace.drop(2).take(5).forEach {
            info(it.toString())
        }
        return tokenObserver.doOnSubscribe {
            subject.onNext(token)
        }
    }

    fun RefreshInteractor.info(message: String) {
        Log.i(this::class.java.simpleName, message)
    }

    /*fun provideTokenRefresh(): Observable<TokenResponse> {
    }*/

    data class AccessToken(val accessToken: String = "")
}
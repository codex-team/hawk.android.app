package so.codex.codexbl.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.entity.UserAuth
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.view.auth.ISignInView

/**
 * Presentor for communication user with sign in
 * @author Shiplayer
 */
class SignInPresenter : BasePresenter<ISignInView>(), KoinComponent {
    /**
     * Interactor for sign in of user
     */
    private val signInInteractor: ISignInInteractor by inject()

    /**
     * Sign in in application
     */
    fun signIn(email: String, password: String) {
        compositeDisposable.of(
                signInInteractor.signIn(UserAuth(email, password))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (it) {
                                view?.successfulLogin()
                            }
                        }, {
                            view?.showErrorMessage(it?.message ?: "Something went wrong")
                        })
        )
    }
}
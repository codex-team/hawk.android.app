package so.codex.codexbl.presenter

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.view.auth.ISignUpView

/**
 * Presentor for communication and handing events for registration new users
 * @author Shiplayer
 */
class SignUpPresenter : BasePresenter<ISignUpView>(), KoinComponent {
    /**
     * Interactor for sending request and registration of new users
     */
    private val signUpInteractor: SignUpInteractor by inject()

    /**
     * Function with checking on validation email and sending request via interactor
     * @param email String representation like as email of new user
     */
    fun submit(email: String) {
        if (email.contains("@")) {
            compositeDisposable.of(
                signUpInteractor.signUp(email)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it) {
                            view?.successfulSignUp()
                        }
                    }, {
                        view?.showErrorMessage(
                            it?.message
                                ?: "Something went wrong"
                        )
                    })
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(SignUpPresenter::class.java.simpleName, "View detached")
    }
}
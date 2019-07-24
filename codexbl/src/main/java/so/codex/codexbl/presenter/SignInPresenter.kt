package so.codex.codexbl.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.base.CompositeDisposable
import so.codex.codexbl.entity.UserAuth
import so.codex.codexbl.interactors.SignInInteractor
import so.codex.codexbl.view.ISignInView

class SignInPresenter : BasePresenter<ISignInView>() {
    private val signInInteractor = SignInInteractor()
    private val compositeDisposable = CompositeDisposable()

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
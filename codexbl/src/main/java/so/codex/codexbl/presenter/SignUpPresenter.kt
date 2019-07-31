package so.codex.codexbl.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.base.CompositeDisposable
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.view.ISignUpView

class SignUpPresenter : BasePresenter<ISignUpView>() {
    private val signInInteractor by lazy {
        SignUpInteractor()
    }
    private val compositeDisposable = CompositeDisposable()

    fun submit(email: String) {
        if (email.contains("@")) {
            compositeDisposable.of(signInInteractor.signUp(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        view?.successfulSignUp()
                    }
                }, {
                    view?.showErrorMessage(it?.message ?: "Something went wrong");
                })
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(SignUpPresenter::class.java.simpleName, "View detached")
    }
}
package so.codex.codexbl.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.entity.UserAuth
import so.codex.codexbl.interactors.ISignInInteractor
import so.codex.codexbl.view.ISignInView

/**
 * Презентор для взаимодействия пользователя со входом
 * @author Shiplayer
 */
class SignInPresenter : BasePresenter<ISignInView>(), KoinComponent {
    private val signInInteractor: ISignInInteractor by inject()

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
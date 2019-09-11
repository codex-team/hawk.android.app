package so.codex.codexbl.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import so.codex.codexbl.base.BasePresenter
import so.codex.codexbl.interactors.SignUpInteractor
import so.codex.codexbl.view.ISignUpView

/**
 * Презентор для взаимодействия и обработки событий для регистрации нового пользователя
 * @author Shiplayer
 */
class SignUpPresenter : BasePresenter<ISignUpView>() {
    /**
     * Интерактор для отправки запроса на регистрацию пользователя
     */
    private val signInInteractor by lazy {
        SignUpInteractor()
    }

    /**
     * Функция, которая проверяет на наличие "собаки" в строке и отправляет запрос через интерактор
     * @param email Строка, в которой должена содержаться почта, по которой будет зарегистрирован
     * новый пользователь
     */
    fun submit(email: String) {
        if (email.contains("@")) {
            compositeDisposable.of(signInInteractor.signUp(email)
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
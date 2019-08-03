package so.codex.hawk.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_in.*
import so.codex.codexbl.presenter.SignInPresenter
import so.codex.codexbl.view.ISignInView
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.router.ILoginRouter
import so.codex.hawk.ui.MainActivity

/**
 * Фрагмент, которые отвечает за вход в приложение
 */
class SignInFragment : BaseFragment(), ISignInView {

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Лямьда выражение, которая создает экземпляр фрагмента и возвращает его
         */
        val instance = {
            SignInFragment()
        }
    }

    val signInPresenter by lazy {
        SignInPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if (et_login.text.isNotEmpty() && et_password.text.isNotEmpty()) {
                // TODO сделать презентер, который будет обрабатывать данное нажатие
                signInPresenter.signIn(et_login.text, et_password.text)
            }
        }

        btn_sign_up.setOnClickListener {
            if (activity is ILoginRouter) {
                (activity as ILoginRouter).showSignUp(et_login.text)
            }
        }

        signInPresenter.attached(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        signInPresenter.detached()
    }

    override fun successfulLogin() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }
}
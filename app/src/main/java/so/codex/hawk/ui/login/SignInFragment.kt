package so.codex.hawk.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class SignInFragment private constructor() : BaseFragment(), ISignInView {

    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if (!et_login.text.isNullOrEmpty() && !et_password.text.isNullOrEmpty()) {
                // TODO сделать презентер, который будет обрабатывать данное нажатие
                signInPresenter.signIn(et_login.text.toString(), et_password.text.toString())
            }
        }

        btn_sign_up.setOnClickListener {
            if (activity is ILoginRouter) {
                (activity as ILoginRouter).showSignUp(et_login.text.toString())
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
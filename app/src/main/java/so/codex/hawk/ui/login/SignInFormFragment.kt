package so.codex.hawk.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_in_form.btn_login
import kotlinx.android.synthetic.main.fragment_sign_in_form.btn_sign_up
import kotlinx.android.synthetic.main.fragment_sign_in_form.et_login
import kotlinx.android.synthetic.main.fragment_sign_in_form.et_password
import so.codex.codexbl.presenter.SignInPresenter
import so.codex.codexbl.view.auth.ISignInView
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.router.ILoginRouter
import so.codex.hawk.ui.MainActivity

/**
 * Fragment form for sign in
 */
class SignInFormFragment : BaseFragment(), ISignInView {

    /**
     * Show error message on screen or field
     */
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Create instance of [SignInFormFragment]
         */
        val instance = {
            SignInFormFragment()
        }
    }

    /**
     * Presenter that handling information from ui and update it
     */
    val signInPresenter by lazy {
        SignInPresenter()
    }

    /**
     * Inflate view from resource and return it. Enable retain instance state
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_sign_in_form, container, false)
    }

    /**
     * Handle arguments that sending from parent
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            if (et_login.text.isNotEmpty() && et_password.text.isNotEmpty()) {
                // TODO сделать презентер, который будет обрабатывать данное нажатие
                signInPresenter.signIn(et_login.text, et_password.text)
            }
        }

        btn_sign_up.setOnClickListener {
            getRouter<ILoginRouter>().showSignUp(et_login.text)
        }

        signInPresenter.attached(this)
    }

    /**
     * Detach view from presenter
     */
    override fun onDestroyView() {
        super.onDestroyView()
        signInPresenter.detached()
    }

    /**
     * Start [MainActivity] after successful Login in service and finish current activity
     */
    override fun successfulLogin() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }
}
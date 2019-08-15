package so.codex.hawk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_sign_up_form.*
import so.codex.codexbl.presenter.SignUpPresenter
import so.codex.codexbl.view.ISignUpView
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.router.ILoginRouter
import so.codex.hawk.ui.login.SignUpFormFragment.Companion.instance

/**
 * Фрагмент для регистрации пользователей, для получения экземпляра данного класса,
 * необходимо вызвать [instance] с необходимыми параметрами
 */
class SignUpFormFragment : BaseFragment(), ISignUpView {
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Ключ, по которому передается значение, которое должно быть вставлено в поле Email.
         */
        public const val EMAIL_KEY = "email_key"

        /**
         * Функция для создания экземпляров данного класса
         * @param email - строка, которая будет вставлена в поле Email. Параметр не обязательный
         * @return Вернет экземпляр класса [SignUpFormFragment] с установленным аргументом
         */
        fun instance(email: String = "") =
            SignUpFormFragment().apply {
                arguments = Bundle().apply {
                    if (email.isNotEmpty())
                        putString(EMAIL_KEY, email)
                }
            }
    }

    private val signUpPresenter by lazy {
        SignUpPresenter()
    }

    private val email by lazy {
        arguments?.getString(EMAIL_KEY, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_sign_up_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_login.text = email
        btn_sign_up.setOnClickListener {
            if (et_login.text.isNotEmpty()) {
                signUpPresenter.submit(et_login.text)
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

        signUpPresenter.attached(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        signUpPresenter.detached()
    }

    override fun successfulSignUp() {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.sign_up_dialog_title)
            .setMessage(R.string.sign_up_dialog_message)
            .setPositiveButton(R.string.button_name_ok) { dialog, _ ->
                dialog.dismiss()
            }
            .setOnDismissListener {
                if (activity is ILoginRouter) {
                    (activity as ILoginRouter).showSignIn()
                }
            }.create().show()
    }

    override fun validateEmail(isValid: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
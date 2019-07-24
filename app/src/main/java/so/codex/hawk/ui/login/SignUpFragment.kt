package so.codex.hawk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up.*
import so.codex.hawk.R
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.router.ILoginRouter
import so.codex.hawk.ui.login.SignUpFragment.Companion.instance

/**
 * Фрагмент для регистрации пользователей, для получения экземпляра данного класса,
 * необходимо вызвать [instance] с необходимыми параметрами
 */
class SignUpFragment private constructor() : BaseFragment() {
    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        /**
         * Ключ, по которому передается значение, которое должно быть вставлено в поле Email.
         */
        public const val EMAIL_KEY = "email_key"

        /**
         * Функция для создания экземпляров данного класса
         * @param email - строка, которая будет вставлена в поле Email. Параметр не обязательный
         * @return Вернет экземпляр класса [SignUpFragment] с установленным аргументом
         */
        fun instance(email: String = "") =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    if (email.isNotEmpty())
                        putString(EMAIL_KEY, email)
                }
            }
    }

    private val email by lazy {
        arguments?.getString(EMAIL_KEY, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_sign_up.setText(email)
        btn_sign_up.setOnClickListener {
            if (!et_sign_up.text.isNullOrEmpty()) {
                // TODO презентер, которые обрабатывает нажатие для регистрации
                if (activity is ILoginRouter) {
                    (activity as ILoginRouter).showSignIn()
                }
            }
        }
    }
}
package so.codex.hawk.ui.login

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_sign_in.*
import so.codex.hawk.R
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.router.ILoginRouter

/**
 * Активити, которая отвечает за вход в приложение
 */
class LoginActivity : BaseSingleFragmentActivity(), ILoginRouter {
    override fun showSignIn() {
        val signUpFragment = supportFragmentManager.findFragmentByTag(SignUpFragment::class.java.simpleName)
        if (signUpFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .remove(signUpFragment)
                    .commit()
        }
        replaceFragment(SignInFragment.instance())
    }

    override fun showSignUp(email: String) {
        replaceAndAdd(SignUpFragment.instance(email))
    }

    companion object {
        /**
         * Ключ, по которому устанавливается значение для открытия необходимого фрагмента.
         * Для открытия экрана входа, достаточно ничего не указывать или указать этот ключ
         * со значением [START_SIGN_IN]
         * Для открытия экрана для регистрации, необходимо указать ключ со значением [START_SIGN_UP]
         */
        public const val LOGIN_ACTIVITY_ACTION_KEY = "login_activity_action_key"
        /**
         * Значние, для открытия экрана для входа
         */
        public const val START_SIGN_IN = 100
        /**
         * Значение, для открытия экрана для регистрации нового пользователя
         */
        public const val START_SIGN_UP = 101
    }

    override val containerId: Int
        get() = R.id.frame_container

    /**
     * Инициализация экрана
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        intent?.extras?.let {
            when (it.getInt(LOGIN_ACTIVITY_ACTION_KEY, START_SIGN_IN)) {
                START_SIGN_IN -> {
                    replaceFragment(SignInFragment.instance())
                }
                START_SIGN_UP -> {
                    replaceAndAdd(SignUpFragment.instance(et_login.text.toString()))
                }
            }
        } ?: replaceFragment(SignInFragment.instance())
    }


}
package so.codex.hawk

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * Активити, которая отвечает за вход в приложение
 */
class LoginActivity : FragmentActivity() {
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

    /**
     * Инициализация экрана
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        intent?.extras?.let {
            when (it.getInt(LOGIN_ACTIVITY_ACTION_KEY, START_SIGN_IN)) {
                START_SIGN_IN -> {

                }
                START_SIGN_UP -> {

                }
            }
        }
    }
}
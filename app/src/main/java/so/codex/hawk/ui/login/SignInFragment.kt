package so.codex.hawk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_in_form.*
import so.codex.hawk.R
import so.codex.hawk.base.InnerSingleFragment
import so.codex.hawk.router.ILoginRouter

class SignInFragment : InnerSingleFragment(), ILoginRouter {

    override val containerId: Int
        get() = R.id.cardForm

    companion object {
        fun instance(bundle: Bundle?) = SignInFragment().apply {
            arguments = bundle
        }

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

    override fun showSignIn() {
        val signUpFragment = childFragmentManager.findFragmentByTag(SignUpFormFragment::class.java.simpleName)
        if (signUpFragment != null) {
            childFragmentManager
                .beginTransaction()
                .remove(signUpFragment)
                .commit()
        }
        replaceFragment(SignInFormFragment.instance())
    }

    override fun showSignUp(email: String) {
        replaceAndAdd(SignUpFormFragment.instance(email))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            when (it.getInt(LOGIN_ACTIVITY_ACTION_KEY, START_SIGN_IN)) {
                START_SIGN_IN -> {
                    replaceFragment(SignInFormFragment.instance())
                }
                START_SIGN_UP -> {
                    replaceAndAdd(SignUpFormFragment.instance(et_login.text))
                }
            }
            if (it.containsKey(LOGIN_ACTIVITY_ACTION_KEY)) {
                it.remove(LOGIN_ACTIVITY_ACTION_KEY)
            }
        } ?: replaceFragment(SignInFormFragment.instance())
    }


    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
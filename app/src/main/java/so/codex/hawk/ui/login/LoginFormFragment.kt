package so.codex.hawk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_in_form.*
import so.codex.hawk.R
import so.codex.hawk.base.InnerSingleFragment
import so.codex.hawk.router.ILoginRouter

/**
 * Fragment that control of sign in or sign up fragment
 */
class LoginFormFragment : InnerSingleFragment(), ILoginRouter {
    /**
     * Container id it is resource that can contain fragment
     */
    override val containerId: Int
        get() = R.id.cardForm

    companion object {
        /**
         * Get instance of [LoginFormFragment] and set up arguments as Bundle [bundle]
         * @param bundle arguments that sending to fragment as that created
         */
        fun instance(bundle: Bundle?) = LoginFormFragment().apply {
            arguments = bundle
        }

        /**
         * Key that set up value for opening necessary fragment.
         * For opening sign in fragment need set up value [START_SIGN_IN]
         * For opening sign up fragment need set up value [START_SIGN_UP]
         * By default open fragment with value [START_SIGN_IN]
         */
        public const val LOGIN_ACTIVITY_ACTION_KEY = "login_activity_action_key"
        /**
         * Value for opening form for sign in
         */
        public const val START_SIGN_IN = 100
        /**
         * Value for opening form for sign up
         */
        public const val START_SIGN_UP = 101
    }

    /**
     * Show sign in fragment
     */
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

    /**
     * Show sign up fragment
     */
    override fun showSignUp(email: String) {
        replaceAndAdd(SignUpFormFragment.instance(email))
    }

    /**
     * Inflate view from resource and return it. Enable retain instance state
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /**
     * Handle arguments that sending from parent
     */
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

    /**
     * Show error message on screen or field
     */
    override fun showErrorMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
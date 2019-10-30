package so.codex.hawk.ui.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import so.codex.hawk.BuildConfig
import so.codex.hawk.R
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.base.InnerSingleFragment

/**
 * Activity that responsibility of authorization of user
 */
class LoginActivity : BaseSingleFragmentActivity() {
    override val containerId: Int
        get() = R.id.frame_container

    /**
     * Initialize of view
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        build_name.text = BuildConfig.VERSION_NAME
        replaceFragment(LoginFormFragment.instance(intent?.extras))
    }

    /**
     * Handle event of invoke on press back button, check if it have child fragment and invoke event in them. If they
     * all return false, then all child fragment handled it event and invoke system event.
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            for (fragment in supportFragmentManager.fragments) {
                if (fragment is InnerSingleFragment) {
                    val handled = fragment.onBackPressed()
                    if (handled) {
                        return
                    }
                }
            }
            super.onBackPressed()
        }
    }
}
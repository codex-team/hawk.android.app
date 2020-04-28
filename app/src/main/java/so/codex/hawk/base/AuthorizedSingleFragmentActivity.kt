package so.codex.hawk.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import so.codex.codexbl.presenter.AuthorizedPresenter
import so.codex.codexbl.view.auth.IAuthorizedView
import so.codex.hawk.router.ILogoutRouter
import so.codex.hawk.ui.login.LoginActivity

/**
 * Abstract class that responsibility on authorization of user. Extend class of [BaseSingleFragmentActivity] and
 * implement interface of [ILogoutRouter].
 */
abstract class AuthorizedSingleFragmentActivity : BaseSingleFragmentActivity(), ILogoutRouter {
    /**
     * Implementation [IAuthorizedView] like as anonymous class.
     */
    private val mAuthorizedView: IAuthorizedView = object :
        IAuthorizedView {
        override fun showErrorMessage(message: String) {

        }

        override fun logout() {
            this@AuthorizedSingleFragmentActivity.logout()
        }

    }

    /**
     * Presenter that responsibility of authorization of user
     */
    private val mAuthPresenter = AuthorizedPresenter()

    /**
     * How will the activity be created then the [mAuthPresenter] is attached to the [mAuthorizedView]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuthPresenter.attached(mAuthorizedView)
    }

    /**
     * Connect authorization view to authorization presenter if view is null
     */
    override fun onStart() {
        super.onStart()
        if (mAuthPresenter.view == null) {
            mAuthPresenter.attached(mAuthorizedView)
        }
    }

    /**
     * Detach view from presenter
     */
    override fun onStop() {
        super.onStop()
        mAuthPresenter.detached()
    }

    /**
     * If user pressed button back, notify about it the [mAuthPresenter]
     */
    fun pressLogout() {
        mAuthPresenter.clearAndLogout()
    }

    /**
     * In while logout, remove and clear all activity in stack and start new activity [LoginActivity]
     */
    override fun logout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        } else {
            finishAffinity()
        }
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
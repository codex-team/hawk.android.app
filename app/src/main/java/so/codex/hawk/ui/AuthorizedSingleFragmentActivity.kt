package so.codex.hawk.ui

import so.codex.codexbl.view.IAuthorizedView
import so.codex.hawk.base.BaseSingleFragmentActivity
import so.codex.hawk.router.ILogoutRouter

abstract class AuthorizedSingleFragmentActivity : BaseSingleFragmentActivity(), ILogoutRouter {
    private val mAuthorizedView: IAuthorizedView = object: IAuthorizedView {
        override fun showErrorMessage(message: String) {

        }

        override fun logout() {
            this@AuthorizedSingleFragmentActivity.logout()
        }

    }

    


}
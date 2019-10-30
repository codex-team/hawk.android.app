package so.codex.codexbl.view

import so.codex.codexbl.view.base.IBaseView

/**
 * Interface with method for logout, if user not authorized
 */
interface IAuthorizedView : IBaseView {
    /**
     * Client logout from application
     */
    fun logout()
}
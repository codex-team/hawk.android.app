package so.codex.codexbl.view.auth

import so.codex.codexbl.view.base.IBaseView

/**
 * Interface for communication with UI in while authorization and sign in
 */
interface ISignInView : IBaseView {
    /**
     * Method invoked if user successfully sign in in application
     */
    fun successfulLogin()
}
package so.codex.codexbl.view.auth

import so.codex.codexbl.view.base.IBaseView

/**
 * Interface for communication UI in while registration
 */
interface ISignUpView : IBaseView {

    /**
     * Method invoked if user successfully registered in application
     */
    fun successfulSignUp()

    /**
     * Method invoked if email valid or not
     */
    fun validateEmail(isValid: Boolean)
}
package so.codex.codexbl.view

import so.codex.codexbl.view.base.IBaseView

/**
 * Представление для взаимодействия с UI во время регистрации
 */
interface ISignUpView : IBaseView {

    /**
     * Метод, который вызывается в случае успешной регистрации
     */
    fun successfulSignUp()

    /**
     * Метод, вызывается во время проверки почты на правильность
     */
    fun validateEmail(isValid: Boolean)
}
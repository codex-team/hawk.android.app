package so.codex.codexbl.view

import so.codex.codexbl.view.base.IBaseView

/**
 * Представление взаимодействия с UI пользователя на экране входа
 */
interface ISignInView : IBaseView {
    /**
     * Метод, который вызывается, когда пользователь успешно вошел в систему
     */
    fun successfulLogin()
}
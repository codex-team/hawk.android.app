package so.codex.codexbl.view

import so.codex.codexbl.view.base.IBaseView

/**
 * Интерфейс в котором определен метод для выхода из приложения, если пользователь не авторизован
 */
interface IAuthorizedView : IBaseView {
    /**
     * Выход из аккаунта
     */
    fun logout()
}
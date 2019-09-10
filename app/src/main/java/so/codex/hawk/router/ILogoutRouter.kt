package so.codex.hawk.router

/**
 * Роутер, который отвечает за выход из аккаунта
 */
interface ILogoutRouter : IBaseRouter {
    fun logout()
}
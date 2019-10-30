package so.codex.hawk.router

/**
 * Router that responsibility for logout of user
 */
interface ILogoutRouter : IBaseRouter {
    /**
     * Client and server logout
     */
    fun logout()
}
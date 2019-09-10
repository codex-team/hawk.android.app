package so.codex.hawk.router

/**
 * Роутер, в котором определены методы для вызыва их на экране с авторизацией
 */
interface ILoginRouter : IBaseRouter {
    /**
     * Показать форму для входа
     */
    fun showSignIn()

    /**
     * Показать форму для регистрации
     * @param email строка, которая будет вставлено как почта для регистрации
     */
    fun showSignUp(email: String = "")
}
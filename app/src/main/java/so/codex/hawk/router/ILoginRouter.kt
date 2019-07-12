package so.codex.hawk.router

interface ILoginRouter : IBaseRouter {
    fun showSignIn()
    fun showSignUp(email: String = "")
}
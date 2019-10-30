package so.codex.hawk.router

/**
 * Router with declared method for showing fragment on Authorization activity
 */
interface ILoginRouter : IBaseRouter {
    /**
     * Show fragment for sign in.
     */
    fun showSignIn()

    /**
     * Show fragment for registration
     * @param email string that insert in edit view in sign up fragment
     */
    fun showSignUp(email: String = "")
}
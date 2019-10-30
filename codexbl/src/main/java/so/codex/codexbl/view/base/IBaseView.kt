package so.codex.codexbl.view.base

/**
 * Base interface with common method for all view
 */
interface IBaseView{
    /**
     * Show error on screen
     * @param message message with text of error
     */
    fun showErrorMessage(message: String)
}
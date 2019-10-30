package so.codex.codexbl.view.base

/**
 * Interface that have method for communication with progress bar
 * @author Shiplayer
 */
interface ILoaderView : IBaseView {
    /**
     * Show indicator of loading
     */
    fun showLoader()

    /**
     * Hide indicator of loading
     */
    fun hideLoader()
}
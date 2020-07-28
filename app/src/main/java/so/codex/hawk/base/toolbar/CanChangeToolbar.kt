package so.codex.hawk.base.toolbar

/**
 * Interface with declared method for updating toolbar by model
 */
interface CanChangeToolbar {

    /**
     * Update toolbar with [ToolbarComponentViewModel]
     */
    fun updateToolbar(viewModel: ToolbarComponentViewModel)
}
package so.codex.hawk.base.text

/**
 * Interface with method for updating text for searching
 */
interface TextListener {
    /**
     * Search something by [text]
     * @param text text for searching
     */
    fun searchText(text: String)
}
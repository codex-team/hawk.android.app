package so.codex.hawk.base.toolbar

import so.codex.hawk.base.text.TextListener

data class ToolbarComponentViewModel(
    val menuIcon: Int = -1,
    val titleIcon: Int = -1,
    val title: String = "",
    val searchText: String = "",
    val showSearchView: Boolean = false,
    val showSearchIcon: Boolean = false,
    val textListener: TextListener? = null
)
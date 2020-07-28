package so.codex.hawk.base.toolbar

import androidx.annotation.DrawableRes
import so.codex.hawk.base.text.TextListener

/**
 * View model that contain all properties for showing or hiding text or view of toolbar
 * @property menuIcon resource for showing in toolbar like as menu
 * @property titleIcon resource for showing in toolbar in title
 * @property title main text for showing that screen user see
 * @property searchText Text for searching something
 * @property showSearchIcon need to show search icon for open search view
 * @property showSearchView for show search view
 * @property textListener listener for searching something
 */
data class ToolbarComponentViewModel(
    @DrawableRes
    val menuIcon: Int = -1,
    @DrawableRes
    val titleIcon: Int = -1,
    val title: String = "",
    val searchText: String = "",
    val showSearchView: Boolean = false,
    val showSearchIcon: Boolean = false,
    val textListener: TextListener? = null
)
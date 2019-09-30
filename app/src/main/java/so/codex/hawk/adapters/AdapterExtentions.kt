package so.codex.hawk.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Here it is extensions for recycler view adapters
 */

/**
 * Used [DiffUtil] for calculation different elements in two lists, updating them on new elements.
 * @param old Old list of elements that adapter have
 * @param new New list of elements that setting to data in adapter
 * @param compare Compare element from old with new and return true if they equals else false
 */
fun <T> RecyclerView.Adapter<*>.notify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                compare(old[oldItemPosition], new[newItemPosition])


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size
    })
    diff.dispatchUpdatesTo(this)
}
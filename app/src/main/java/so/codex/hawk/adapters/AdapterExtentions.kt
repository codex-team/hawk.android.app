package so.codex.hawk.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Здесь находятся все необходимые расширения для классов [RecyclerView.Adapter]
 */

/**
 * Данное расширение используется для уведомления адаптера с помощью [DiffUtil], которые сам
 * высчитывает позиции элементов, которых необходимо обновить.
 * @param old Старый список элементов, которые находятся в адапторе
 * @param new Новый список элементов, которые должны будут поместиться в адаптер
 * @param compare Лямбда выражение с помощью которого происходит сравнивание двух элементов,
 * одного из старого списка [old] с элементом из нового [new]
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
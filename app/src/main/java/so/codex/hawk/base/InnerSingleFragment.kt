package so.codex.hawk.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import so.codex.hawk.router.IBaseRouter

/**
 * Используется для отображение внутреннего сменяющегося фрагмента, с сохранением конкретного
 * фрагмента, который был виден последний раз
 */
abstract class InnerSingleFragment : BaseFragment(), IBaseRouter {
    abstract val containerId: Int

    companion object {
        private const val SAVED_INNER_FRAGMENT_NAME_KEY = "saved_inner_fragment_name_key"
    }

    /**
     * Находит фрагмент в стеке, если он там есть, если его нет, то просто вставляет его в
     * [containerId] без добавления в стек
     * @param fragment инстансе фрагмента, которого хотим заменить
     */
    override fun replaceFragment(fragment: Fragment) {
        val oldFragment = childFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        childFragmentManager
                .beginTransaction()
                .replace(containerId, oldFragment ?: fragment, fragment::class.java.simpleName)
                .commit()
    }

    /**
     * Находит фрагмент в стеке, если он там есть, если его нет, то просто вставляет его в
     * [containerId], а также добавляет его в стек, для дальнейшего переиспользования
     * @param fragment инстансе фрагмента, которого хотим заменить
     * @param tag уникальное название фрагмента, которого можно будет найти в стеке
     */
    override fun replaceAndAdd(fragment: Fragment, tag: String?) {
        val oldFragment = childFragmentManager.findFragmentByTag(
                tag
                        ?: fragment::class.java.simpleName
        )
        oldFragment?.arguments = fragment.arguments
        childFragmentManager
                .beginTransaction()
                .replace(
                        containerId, oldFragment ?: fragment, tag
                        ?: fragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
    }

    /**
     * Находит фрагмент в стеке по [tag], если он там есть, если его нет, то просто вставляет его в
     * [containerId]
     * @param tag Уникальное название фрагмента в стеке
     */
    private fun replaceByFragmentName(tag: String) {
        val oldFragment = childFragmentManager.findFragmentByTag(tag)
        //oldFragment?.arguments = fragment.arguments
        if (oldFragment != null) {
            childFragmentManager
                    .beginTransaction()
                    .replace(containerId, oldFragment, tag)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val showedFragment = childFragmentManager.findFragmentById(containerId)
        val tag = if (showedFragment != null) showedFragment::class.java.simpleName else ""
        outState.putString(SAVED_INNER_FRAGMENT_NAME_KEY, tag)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            val tag = it.getString(SAVED_INNER_FRAGMENT_NAME_KEY)
            if (!tag.isNullOrEmpty())
                replaceByFragmentName(tag)
        }
    }

    /**
     * Проверяем, если в нашем стеке нет больше фрагментов, то выходим, в противном случае,
     * вызываем у каждого вложенного фрагмента [InnerSingleFragment.onBackPressed]
     * @return возвращает true, если удалось удалить из стека фрагмент и false, если не удалось или
     * элементы все уже произвели данное событие
     */
    fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            true
        } else {
            for (fragment in childFragmentManager.fragments) {
                if (fragment is InnerSingleFragment) {
                    val handled = fragment.onBackPressed()
                    if (handled) {
                        return true
                    }
                }
            }
            false
        }
    }
}
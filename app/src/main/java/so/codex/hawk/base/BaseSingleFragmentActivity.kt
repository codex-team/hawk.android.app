package so.codex.hawk.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import so.codex.hawk.router.IBaseRouter

/**
 * Абстрактный класс для активти, который содержит только один контейнер для фрагментов. Также
 * обладает способностью восстанавливать фрагмент после изменения системных настроек.
 */
abstract class BaseSingleFragmentActivity : FragmentActivity(), IBaseRouter {
    /**
     * Уникальный id по которому происходит установка и замена фрагментов для отображения их на
     * layout
     */
    protected abstract val containerId: Int

    companion object {
        /**
         * Ключ по которому сохраняются и текущий фрагмент в контейнере [containerId]
         */
        private const val SAVED_FRAGMENT_NAME_KEY = "saved_fragment_name_key"
    }

    /**
     * Находит фрагмент в стеке, если он там есть, если его нет, то просто вставляет его в
     * [containerId] без добавления в стек
     * @param fragment инстансе фрагмента, которого хотим заменить
     */
    override fun replaceFragment(fragment: Fragment) {
        val oldFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        supportFragmentManager
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
        val oldFragment = supportFragmentManager.findFragmentByTag(tag
                                                                           ?: fragment::class.java.simpleName)
        oldFragment?.arguments = fragment.arguments
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, oldFragment ?: fragment, tag
                        ?: fragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
    }

    /**
     * Находит фрагмент в стеке по [tag], если он там есть, если его нет, то просто вставляет его в
     * [containerId]
     * @param tag Уникальное название фрагмента в стеке
     */
    private fun replaceByFragmentName(tag: String) {
        val oldFragment = supportFragmentManager.findFragmentByTag(tag)
        //oldFragment?.arguments = fragment.arguments
        if (oldFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(containerId, oldFragment, tag)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val showedFragment = supportFragmentManager.findFragmentById(containerId)
        val tag = if (showedFragment != null) showedFragment::class.java.simpleName else ""
        outState?.putString(SAVED_FRAGMENT_NAME_KEY, tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            val tag = it.getString(SAVED_FRAGMENT_NAME_KEY)
            if(!tag.isNullOrEmpty())
                replaceByFragmentName(tag)
        }
    }
}
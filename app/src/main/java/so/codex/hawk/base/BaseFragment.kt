package so.codex.hawk.base

import androidx.fragment.app.Fragment
import so.codex.codexbl.view.base.IBaseView
import so.codex.hawk.router.IBaseRouter

/**
 * Родительский фрагмент, который описывает общую логику для всех фрагментов
 */
abstract class BaseFragment : Fragment(), IBaseView {
    /**
     * Получение роутера с типом [R]. Проверяет, является ли родительский фрагмент или активити
     * наследником данного типа [R], если такой имеется, то возвращаем его, если нет, то бросаем
     * исключение [ClassCastException]
     * @return Возвращает роутер с указанным типом [R]
     */
    inline fun <reified R : IBaseRouter> getRouter(): R {
        return when {
            parentFragment is R -> parentFragment as R
            activity is R -> activity as R
            else -> throw ClassCastException()
        }
    }
}
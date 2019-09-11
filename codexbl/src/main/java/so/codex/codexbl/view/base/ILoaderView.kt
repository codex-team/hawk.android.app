package so.codex.codexbl.view.base

/**
 * Интерфейс, в котором обявлены методы для взаимодействия со скрытием или показом индикатора
 * загрузки
 */
interface ILoaderView : IBaseView {
    /**
     * Показать индикатор загрузки
     */
    fun showLoader()

    /**
     * Скрыть индикатор загрузки
     */
    fun hideLoader()
}
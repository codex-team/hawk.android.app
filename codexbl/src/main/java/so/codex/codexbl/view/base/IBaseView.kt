package so.codex.codexbl.view.base

/**
 * Основной интерфейс, который определяет основной функционал для всех представлений
 */
interface IBaseView{
    /**
     * Показать ошибку на экране
     * @param message сообещние, которое необходимо показать пользователю
     */
    fun showErrorMessage(message: String)
}
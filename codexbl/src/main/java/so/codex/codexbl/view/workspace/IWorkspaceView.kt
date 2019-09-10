package so.codex.codexbl.view.workspace

import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.ILoaderView

/**
 * Интерфейс c объявленными методами для работы с [Workspace], с помощью которого можно
 * взаимодействовать с UI
 */
interface IWorkspaceView : ILoaderView {
    /**
     * Показать все проекты из списка [Workspace]
     */
    fun showProjects(workspaces: List<Workspace>)

    /**
     * Вызывается, если в списке [Workspace] нет созданных проектов
     */
    fun showEmptyProjects()
}
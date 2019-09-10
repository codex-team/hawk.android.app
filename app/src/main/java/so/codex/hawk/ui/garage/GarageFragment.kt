package so.codex.hawk.ui.garage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_garage.*
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.presenter.workspace.WorkspacePresenter
import so.codex.codexbl.view.workspace.IWorkspaceView
import so.codex.hawk.R
import so.codex.hawk.adapters.ProjectsItemsAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.base.BaseFragment

/**
 * Фрагмент для отображение проектов, которые хранятся в воркспейсе
 * Реализует интерфейс [IWorkspaceView], где определены основные методы для взаимодействия с
 * Workspace
 */
class GarageFragment : BaseFragment(), IWorkspaceView {
    companion object {
        /**
         * Для получения объекта
         */
        fun instance() = GarageFragment()
    }

    /**
     * Лениява инициализация презентора, который будет обрабатывать события
     */
    private val presenter by lazy {
        WorkspacePresenter()
    }

    /**
     * Показать все проекты, которые есть в [Workspace]
     * @param workspaces список воркспейсов, в которых есть проекты
     */
    override fun showProjects(workspaces: List<Workspace>) {
        if (rv_project_list.visibility == View.GONE) {
            rv_project_list.visibility = View.VISIBLE
            tv_empty_workspace.visibility = View.GONE
        }
        if (rv_project_list.adapter is ProjectsItemsAdapter) {
            (rv_project_list.adapter as ProjectsItemsAdapter).data = workspaces.fold(mutableListOf()) { list, w ->
                list.addAll(w.projects)
                list
            }
        }
    }

    /**
     * Показать прогресс бар
     */
    override fun showLoader() {
        rv_refresh_layout.isRefreshing = true
    }

    /**
     * Скрыть прогресс бар
     */
    override fun hideLoader() {
        rv_refresh_layout.isRefreshing = false
    }

    /**
     * Показать сообщение, что список проектов пустой
     */
    override fun showEmptyProjects() {
        rv_project_list.visibility = View.GONE
        tv_empty_workspace.visibility = View.VISIBLE
    }

    /**
     * Показать ошибку, если она возникла
     */
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_garage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attached(this)

        rv_refresh_layout.setOnRefreshListener {
            presenter.loadAllWorkspaces()
        }

        rv_project_list.apply {
            adapter = ProjectsItemsAdapter()
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        fa_exit.setOnClickListener {
            if (activity is AuthorizedSingleFragmentActivity) {
                (activity as AuthorizedSingleFragmentActivity).pressLogout()
            }
        }
        presenter.loadAllWorkspaces()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detached()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}
package so.codex.hawk.ui.garage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_garage.fa_exit
import kotlinx.android.synthetic.main.fragment_garage.rv_project_list
import kotlinx.android.synthetic.main.fragment_garage.rv_refresh_layout
import kotlinx.android.synthetic.main.fragment_garage.tv_empty_workspace
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.presenter.workspace.WorkspacePresenter
import so.codex.codexbl.view.workspace.IWorkspaceView
import so.codex.hawk.R
import so.codex.hawk.adapters.ProjectsItemsAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.base.BaseFragment

/**
 * Fragment for showing all information about Garage, showing list of project
 * Implementation interface [IWorkspaceView], where declared method for communication with workspace
 */
class GarageFragment : BaseFragment(), IWorkspaceView {
    companion object {
        /**
         * Create and getting fragment
         */
        fun instance() = GarageFragment()
    }

    /**
     * Initialize presenter of workspace
     */
    private val presenter by lazy {
        WorkspacePresenter()
    }

    /**
     * Show all projects in [Workspace]
     * @param workspaces list of [Workspace] that contain projects
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
     * Show progress bar
     */
    override fun showLoader() {
        rv_refresh_layout.isRefreshing = true
    }

    /**
     * Hide progress bar
     */
    override fun hideLoader() {
        rv_refresh_layout.isRefreshing = false
    }

    /**
     * Show message if list of projects is empty
     */
    override fun showEmptyProjects() {
        rv_project_list.visibility = View.GONE
        tv_empty_workspace.visibility = View.VISIBLE
    }

    /**
     * Show error if it occurred
     */
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Create and inflate view from layout and set retain instance for restoring state
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_garage, container, false)
    }

    /**
     * Attack view to presenter and init on click callbacks
     */
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

    /**
     * Detach from view after destroying view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detached()
    }

    /**
     * Unsubscribe of all RxJava streams
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }
}
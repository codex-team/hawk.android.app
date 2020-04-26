package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_project.*
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.presenter.workspace.WorkspacePresenter
import so.codex.codexbl.view.workspace.IWorkspaceView
import so.codex.hawk.R
import so.codex.hawk.SelectedWorkspaceListener
import so.codex.hawk.adapters.ProjectsItemsAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.base.text.TextListener
import so.codex.hawk.base.toolbar.CanChangeToolbar
import so.codex.hawk.base.toolbar.ToolbarComponentViewModel

/**
 * Fragment for showing all information about Garage, showing list of project
 * Implementation interface [IWorkspaceView], where declared method for communication with workspace
 */
class ProjectFragment : BaseFragment(), IWorkspaceView, SelectedWorkspaceListener, TextListener {
    companion object {
        private const val WORKSPACE_KEY = "workspace"

        /**
         * Create and getting fragment
         * @param workspace given workspace
         */
        fun instance(workspace: Workspace?): ProjectFragment {
            val fragment = ProjectFragment()
            val bundle = Bundle()
            bundle.putParcelable(WORKSPACE_KEY, workspace)
            fragment.arguments = bundle

            return fragment
        }
    }

    private val toolbarViewModel = ToolbarComponentViewModel(
        title = "All projects",
        textListener = this
    )

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
        val adapter = rv_project_list.adapter
        if (adapter is ProjectsItemsAdapter) {
            adapter.data =
                workspaces.fold(mutableListOf()) { list, w ->
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_project, container, false)
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

        if (arguments?.getParcelable<Workspace>(WORKSPACE_KEY) == null) {
            presenter.loadAllWorkspaces()
        } else {
            TODO("Load project for one workspace")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is CanChangeToolbar) {
            (activity as CanChangeToolbar).updateToolbar(
                ToolbarComponentViewModel(
                    menuIcon = R.drawable.ic_hamburger,
                    title = "All projects",
                    showSearchIcon = true
                )
            )
        }
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

    override fun select(workspace: Workspace) {
        TODO("Not yet implemented")
    }

    override fun searchText(text: String) {
        presenter.loadAllWorkspaces()
    }
}
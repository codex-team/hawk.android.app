package so.codex.hawk.ui.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.fragment_project.fa_exit
import kotlinx.android.synthetic.main.fragment_project.rv_project_list
import kotlinx.android.synthetic.main.fragment_project.rv_refresh_layout
import kotlinx.android.synthetic.main.fragment_project.tv_empty_workspace
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.base.IReactiveBaseView
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
class ProjectFragment : BaseFragment(), IProjectView, SelectedWorkspaceListener, TextListener {
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

    private val adapter = ProjectsItemsAdapter()

    /**
     * Initialize presenter of workspace
     */
    private val presenter by lazy {
        ProjectPresenter()
    }

    /**
     * Show progress bar
     *//*
    override fun showLoader() {
        rv_refresh_layout.isRefreshing = true
    }

    */
    /**
     * Hide progress bar
     *//*
    override fun hideLoader() {
        rv_refresh_layout.isRefreshing = false
    }

    */
    /**
     * Show message if list of projects is empty
     *//*
    override fun showEmptyProjects() {
        rv_project_list.visibility = View.GONE
        tv_empty_workspace.visibility = View.VISIBLE
    }*/

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
            presenter.submitUiEvent(IProjectView.UiEvent.Refresh)
        }

        rv_project_list.apply {
            adapter = this@ProjectFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        fa_exit.setOnClickListener {
            if (activity is AuthorizedSingleFragmentActivity) {
                (activity as AuthorizedSingleFragmentActivity).pressLogout()
            }
        }

        if (arguments?.getParcelable<Workspace>(WORKSPACE_KEY) == null) {
            //presenter.loadAllWorkspaces()
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

    override fun select(workspace: Workspace) {
        TODO("Not yet implemented")
    }

    override fun searchText(text: String) {
        //presenter.loadAllWorkspaces()
    }

    override fun showUi(model: IProjectView.ProjectViewModel) {
        Log.i("ProjectFragment", "show ui models ${model.projects}")
        rv_refresh_layout.isRefreshing = model.showLoader
        adapter.data = model.projects
        adapter.notifyDataSetChanged()
        if (!model.projects.isEmpty()) {
            tv_empty_workspace.visibility = View.VISIBLE
        } else {
            tv_empty_workspace.visibility = View.GONE
        }
    }

    override fun observeUiEvent(): Observable<IReactiveBaseView.UiEvent> {
        return Observable.empty()
    }
}
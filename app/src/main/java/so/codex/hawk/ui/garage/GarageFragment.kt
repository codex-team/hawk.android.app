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

class GarageFragment : BaseFragment(), IWorkspaceView {
    companion object {
        fun instance() = GarageFragment()
    }

    private val presenter by lazy {
        WorkspacePresenter()
    }

    override fun showWorkspaces(workspace: List<Workspace>) {
        if (rv_project_list.visibility == View.GONE) {
            rv_project_list.visibility = View.VISIBLE
            tv_empty_workspace.visibility = View.GONE
        }
        if (rv_project_list.adapter is ProjectsItemsAdapter) {
            (rv_project_list.adapter as ProjectsItemsAdapter).data = workspace.fold(mutableListOf()) { list, w ->
                list.addAll(w.projects)
                list
            }
        }
    }

    override fun showLoader() {
        rv_refresh_layout.isRefreshing = true
    }

    override fun hideLoader() {
        rv_refresh_layout.isRefreshing = false
    }

    override fun showEmptyWorkspace() {
        rv_project_list.visibility = View.GONE
        tv_empty_workspace.visibility = View.VISIBLE
    }

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
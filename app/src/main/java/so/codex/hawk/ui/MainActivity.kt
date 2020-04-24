package so.codex.hawk.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import so.codex.codexbl.entity.Project
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.IMainView
import so.codex.codexbl.presenter.MainPresenter
import so.codex.hawk.R
import so.codex.hawk.adapters.WorkspaceItemAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.ui.garage.GarageFragment

/**
 * Main activity that showed after authorization
 */
class MainActivity : AuthorizedSingleFragmentActivity(),
    IMainView {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    private val presenter by lazy {
        MainPresenter()
    }

    private val adapter by lazy {
        WorkspaceItemAdapter()
    }

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attached(this)
        setContentView(R.layout.activity_main)
        presenter.loadAllWorkspaces()
        drawer_recycler.adapter = adapter
        drawer_recycler.layoutManager = LinearLayoutManager(this)
        replaceFragment(GarageFragment.instance())
    }

    override fun showWorkspaces(workspaces: List<Workspace>) {
        adapter.setData(workspaces)
    }

    override fun showStartWorkspace() {
        val workspace = Workspace(name = "Add workspace")
        adapter.setLastElem(workspace)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

}

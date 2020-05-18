package so.codex.hawk.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import so.codex.codexbl.entity.Profile
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.presenter.GaragePresenter
import so.codex.codexbl.view.IGarageView
import so.codex.hawk.DefaultImageLoader
import so.codex.hawk.R
import so.codex.hawk.WorkspaceViewModelDiffUtil
import so.codex.hawk.adapters.WorkspaceItemAdapter
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.ui.garage.GarageFragment

/**
 * Main activity that showed after authorization
 */
class MainActivity : AuthorizedSingleFragmentActivity(),
    IGarageView {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    private val presenter by lazy {
        GaragePresenter()
    }

    private val adapter by lazy {
        WorkspaceItemAdapter(object : GaragePresenter.WorkspaceSelectedCallback {
            override fun select(workspace: IGarageView.WorkspaceViewModel) {
                presenter.selectWorkspace(workspace)
            }

        })
    }

    private val diffUtil = WorkspaceViewModelDiffUtil()

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attached(this)

        presenter.load()
        drawer_recycler.adapter = adapter
        drawer_recycler.layoutManager = LinearLayoutManager(this)
        drawer_recycler.itemAnimator = null
        replaceFragment(GarageFragment.instance())
    }

    override fun showWorkspaces(workspaces: List<IGarageView.WorkspaceViewModel>) {
        diffUtil.update(adapter.workspaces, workspaces)
        adapter.workspaces = workspaces
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(adapter)
    }

    override fun showAddWorkspace() {
        adapter.setLastElem(presenter.workspaceMapper(Workspace()))
    }

    override fun showHeader(profile: Profile) {
        header_email.text = profile.email
        if (profile.picture.trim().isEmpty()) {
            header_user_icon.setImageBitmap(DefaultImageLoader.get(profile.id, profile.name).loadImage())
        } else
        Picasso.get()
            .load(profile.picture)
            .error(R.drawable.ic_error_outline_black_24dp)
            .into(header_user_icon)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

}

package so.codex.hawk.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import so.codex.codexbl.entity.Profile
import so.codex.codexbl.entity.Workspace
import so.codex.codexbl.view.IGarageView
import so.codex.codexbl.presenter.GaragePresenter
import so.codex.hawk.R
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
        WorkspaceItemAdapter()
    }

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
        replaceFragment(GarageFragment.instance())
    }

    override fun showWorkspaces(workspaces: List<Workspace>) {
        adapter.setData(workspaces)
    }

    override fun showAddWorkspace() {
        adapter.setLastElem(Workspace())
    }

    override fun showHeader(profile: Profile) {
        header_email.text = profile.email ?: "NULLLLLLL" // is null
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

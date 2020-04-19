package so.codex.hawk.ui

import android.os.Bundle
import so.codex.hawk.R
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.ui.garage.ProjectFragment

/**
 * Main activity that showed after authorization
 */
class GarageActivity : AuthorizedSingleFragmentActivity() {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage)
        val instance = ProjectFragment.instance()
        val bundle = Bundle()
        bundle.putParcelable("workspaces", null)
        instance.arguments = bundle
        replaceFragment(instance)
        instance.select()
    }
}

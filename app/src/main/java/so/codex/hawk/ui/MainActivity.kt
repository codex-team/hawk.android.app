package so.codex.hawk.ui

import android.os.Bundle
import so.codex.codexbl.entity.ProjectCommon
import so.codex.hawk.R
import so.codex.hawk.base.AuthorizedSingleFragmentActivity
import so.codex.hawk.router.IMainRouter
import so.codex.hawk.ui.garage.GarageFragment
import so.codex.hawk.ui.project.ProjectFragment

/**
 * Main activity that showed after authorization
 */
class MainActivity : AuthorizedSingleFragmentActivity(), IMainRouter {
    /**
     * Container for fragment
     */
    override val containerId: Int = R.id.container

    /**
     * Create activity and replace on Garage fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceAndAdd(GarageFragment.instance())
    }

    override fun openProjectFragment(project: ProjectCommon?) {
        replaceAndAdd(ProjectFragment.instance(project))
    }

    override fun openGarageFragment() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

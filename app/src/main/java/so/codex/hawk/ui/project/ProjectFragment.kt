package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext
import so.codex.codexbl.entity.ProjectCommon
import so.codex.codexbl.main.ImageProvider
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.ui.anko.ProjectUI

class ProjectFragment : BaseFragment() {

    lateinit var ui: ProjectUI<ProjectFragment>

    companion object {
        private const val PROJECT_KEY_ARGUMENT = "project_key_argument"

        fun instance(project: ProjectCommon?) = ProjectFragment().apply {
            arguments = Bundle().apply {
                if (project != null)
                    putParcelable(PROJECT_KEY_ARGUMENT, project)
            }
        }
    }

    override fun showErrorMessage(message: String) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ProjectUI<ProjectFragment>().also {
        ui = it
    }.createView(AnkoContext.create(context!!, this)).also {
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {

        } else if (arguments != null) {
            arguments?.getParcelable<ProjectCommon>(PROJECT_KEY_ARGUMENT)?.also {
                ui.toolbarTitle = it.name
                //ui.toolbarIcon = ImageProvider.instance.getImageByUuid( )
            }
        }
    }
}
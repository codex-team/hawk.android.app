package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import org.jetbrains.anko.AnkoContext
import so.codex.codexbl.entity.ProjectCommon
import so.codex.codexbl.main.ImageProvider
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.ui.anko.ProjectContent
import so.codex.hawk.ui.anko.ProjectToolbar
import so.codex.hawk.ui.anko.ProjectUI
import so.codex.utils.getColorById

class ProjectFragment : BaseFragment() {

    lateinit var toolbar: ProjectToolbar<ProjectFragment>
    lateinit var content: ProjectContent<ProjectFragment>

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
        toolbar = it.toolbar
        content = it.content
    }.createView(AnkoContext.create(context!!, this)).also {
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {

        } else if (arguments != null) {
            arguments?.getParcelable<ProjectCommon>(PROJECT_KEY_ARGUMENT)?.also {
                toolbar.toolbarTitle = it.name
                toolbar.toolbarIcon = ImageProvider.instance.getImageByUuid(
                    context!!,
                    it.id,
                    it.name,
                    getColorById(it.id)
                ).toDrawable(resources)
            }
        }
    }
}
package so.codex.hawk.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._LinearLayout
import so.codex.codexbl.entity.Event
import so.codex.codexbl.entity.Payload
import so.codex.codexbl.entity.ProjectCommon
import so.codex.codexbl.main.ImageProvider
import so.codex.codexbl.view.project.IProjectView
import so.codex.hawk.base.BaseFragment
import so.codex.hawk.ui.anko.ProjectContent
import so.codex.hawk.ui.anko.ProjectToolbar
import so.codex.hawk.ui.anko.ProjectUI
import so.codex.utils.getColorById
import java.util.*

class ProjectFragment : BaseFragment(), IProjectView {

    lateinit var toolbar: ProjectToolbar<_LinearLayout>
    lateinit var content: ProjectContent<_LinearLayout>
    lateinit var projectUI: ProjectUI<ProjectFragment>

    private var adapter = ErrorItemAdapter()

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
        projectUI = it
    }.createView(AnkoContext.create(context!!, this)).also {
        retainInstance = true
        toolbar = projectUI.toolbar
        content = projectUI.content
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
                content.rv.layoutManager = LinearLayoutManager(context)
                content.rv.adapter = adapter
                adapter.dataEvents = getFakeEvents()
            }
        }
    }

    fun getFakeEvents() = listOf(
            Event("1","2", Payload("Error1", Date())),
            Event("2","25", Payload("Error2", Date())),
            Event("3","26", Payload("Error3", Date())),
            Event("4","27", Payload("Error4", Date()))
    )


    override fun showPlotData() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showHawkErrors(events: List<Event>) {

    }

    override fun openEvent(event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package so.codex.hawk.ui.anko

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

class ProjectUI<T> : AnkoComponent<T> {
    public lateinit var toolbar: ProjectToolbar<T>
    public lateinit var content: ProjectContent<T>

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        verticalLayout {
            ProjectToolbar<T>().also {
                toolbar = it
            }.createView(ui)
            ProjectContent<T>().also {
                content = it
            }.createView(ui)
        }
    }

}
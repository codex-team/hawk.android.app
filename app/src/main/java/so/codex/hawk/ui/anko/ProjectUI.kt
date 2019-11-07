package so.codex.hawk.ui.anko

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.verticalLayout

class ProjectUI<T> : AnkoComponent<T> {
    public lateinit var toolbar: ProjectToolbar<_LinearLayout>
    public lateinit var content: ProjectContent<_LinearLayout>

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        verticalLayout {
            ProjectToolbar<_LinearLayout>().also {
                toolbar = it
            }.createView(AnkoContext.createDelegate(this))
            ProjectContent<_LinearLayout>().also {
                content = it
            }.createView(AnkoContext.createDelegate(this))
        }
    }

}
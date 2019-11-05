package so.codex.hawk.ui.anko

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout

class ProjectContent<T> : AnkoComponent<T> {
    public lateinit var rv: RecyclerView

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        verticalLayout {
            frameLayout {
                backgroundColor = Color.parseColor("#242732")
            }.lparams(matchParent, dip(131))
            recyclerView {
                rv = this
            }.lparams(matchParent, matchParent) {
                topMargin = dip(20)
                bottomMargin = dip(5)
            }

            lparams(matchParent)
        }
    }

}
package so.codex.hawk.ui.anko

import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

class ErrorItemView<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            textView {
                maxLines = 2
            }.lparams(matchParent, wrapContent) {
                weight = 1f
            }
            textView {

            }
        }
    }

}
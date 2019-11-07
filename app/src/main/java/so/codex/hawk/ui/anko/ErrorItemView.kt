package so.codex.hawk.ui.anko

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent
import so.codex.hawk.R

class ErrorItemView<T> : AnkoComponent<T> {
    companion object {
        val messageId: Int = R.id.errorTitle
        val countId: Int = R.id.errorCount
        val imageId: Int = R.id.errorUserImage
    }

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.HORIZONTAL
            textView {
                id = messageId
                maxLines = 2
                typeface = Typeface.create("roboto", Typeface.NORMAL)
            }.lparams(matchParent, wrapContent) {
                weight = 1f
            }
            textView {
                id = countId
            }.lparams(dip(36), wrapContent) {
                gravity = Gravity.CENTER_VERTICAL
            }
            imageView {
                id = imageId
            }.lparams(dip(20), dip(20)) {
                gravity = Gravity.CENTER_VERTICAL
            }
            lparams(matchParent, wrapContent)
        }
    }

}
package so.codex.hawk.ui.anko

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.jetbrains.anko.*
import org.jetbrains.anko.design.themedAppBarLayout
import so.codex.hawk.R
import so.codex.uicomponent.BorderedImageView
import kotlin.properties.Delegates

class ProjectUI<T> : AnkoComponent<T> {
    private lateinit var mTitle: TextView
    private lateinit var mIcon: BorderedImageView
    private lateinit var mBell: ImageView

    var toolbarTitle by Delegates.observable("") { _, _, new ->
        mTitle.text = new
    }

    var toolbarIcon: Drawable? by Delegates.observable(null as Drawable?) { _, _, new ->
        mIcon.setImageDrawable(new)
    }

    override fun createView(ui: AnkoContext<T>): View = with(ui) {
        verticalLayout {
            themedAppBarLayout(theme = R.style.Widget_Design_AppBarLayout) {
                toolbar {
                    linearLayout {
                        background = null
                        borderedImageView {
                            //mIcon = this
                            image = ContextCompat.getDrawable(ui.ctx, R.drawable.ic_test_layer)
                            visibility = View.VISIBLE
                            background = null
                            adjustViewBounds = true
                            setCorners(dip(5))
                        }.lparams(wrapContent, wrapContent) {
                            gravity = Gravity.CENTER
                        }
                        textView("codex.so").apply {
                            mTitle = this
                            background = null
                        }.lparams(matchParent, wrapContent) {
                            gravity = Gravity.CENTER
                            weight = 1f
                        }
                        imageView {
                            mBell = this
                            image = ContextCompat.getDrawable(ui.ctx, R.drawable.ic_bell)
                        }.lparams(wrapContent, wrapContent) {
                            horizontalMargin = dip(16)
                            gravity = Gravity.CENTER
                        }
                    }.lparams(matchParent, matchParent)
                }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize))
            }.lparams(width = matchParent)
        }
    }
}
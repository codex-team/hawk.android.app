package so.codex.hawk.ui.anko

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.dimenAttr
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.image
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView
import org.jetbrains.anko.toolbar
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.verticalMargin
import org.jetbrains.anko.wrapContent
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
            appBarLayout {
                toolbar {
                    linearLayout {
                        borderedImageView {
                            mIcon = this
                            image = ContextCompat.getDrawable(ui.ctx, R.drawable.ic_launcher_foreground)
                            visibility = View.VISIBLE
                            background = null
                            adjustViewBounds = true
                            setCorners(dip(5))
                        }.lparams(wrapContent, wrapContent) {
                            gravity = Gravity.CENTER
                            verticalMargin = dip(10)
                        }
                        textView("codex.so").apply {
                            mTitle = this
                            background = null
                            textColor = ContextCompat.getColor(ui.ctx, R.color.very_light_blue)
                            textSize = dip(15).toFloat()
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
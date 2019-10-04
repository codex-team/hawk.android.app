package so.codex.hawk.ui.anko

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import so.codex.uicomponent.BorderedImageView

inline fun ViewManager.borderedImageView(theme: Int = 0, init: (BorderedImageView).() -> Unit): BorderedImageView {
    return ankoView({
        BorderedImageView(it)
    }, theme, init)
}
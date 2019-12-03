package so.codex.hawk.ui.base

import android.content.Context
import android.view.View
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.frameLayout

class ToolbarUi(override val ctx: Context) : Ui {

    override val root: View
        get() = frameLayout {

        }
}
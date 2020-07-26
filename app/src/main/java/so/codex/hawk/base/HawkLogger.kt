package so.codex.hawk.base

import android.util.Log
import so.codex.codexbl.base.Logger

object HawkLogger : Logger {
    override fun info(tag: String, message: String) {
        Log.i(tag, message)
    }
}
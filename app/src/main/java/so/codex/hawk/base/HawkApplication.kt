package so.codex.hawk.base

import androidx.multidex.MultiDexApplication
import so.codex.codexbl.main.CodexKoinComponent

class HawkApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        CodexKoinComponent.start(this)
    }
}
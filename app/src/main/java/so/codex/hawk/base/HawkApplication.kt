package so.codex.hawk.base

import androidx.multidex.MultiDexApplication
import so.codex.codexbl.main.StartKoinComponent

class HawkApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        StartKoinComponent.start(this)
    }
}
package so.codex.hawk.base

import android.app.Application
import so.codex.codexbl.main.StartKoinComponent

class HawkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StartKoinComponent.start(this)
    }
}
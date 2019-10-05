package so.codex.hawk.base

import androidx.multidex.MultiDexApplication
import so.codex.codexbl.main.CodexKoinComponent

/**
 * Main class of application for building multi dex
 */
class HawkApplication : MultiDexApplication() {

    /**
     * Initialize of koin dependencies graph
     */
    override fun onCreate() {
        super.onCreate()
        CodexKoinComponent.start(this)
    }
}
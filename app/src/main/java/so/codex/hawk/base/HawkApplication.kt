package so.codex.hawk.base

import androidx.multidex.MultiDexApplication
import so.codex.codexbl.main.CodexKoinComponent

/**
 * Main class of application for building multi dex with over 64K methods. Used for building more that single dex file.
 */
class HawkApplication : MultiDexApplication() {

    /**
     * Initialize of koin dependencies graph
     */
    override fun onCreate() {
        super.onCreate()
        CodexKoinComponent.start(this)
        CodexKoinComponent.updateDependencies(CodexKoinComponent.ScopeDependencies.GLOBAL_SCOPE)
        CodexKoinComponent.updateDependencies(CodexKoinComponent.ScopeDependencies.LOGIN_SCOPE)
    }
}

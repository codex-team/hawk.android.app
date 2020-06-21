package so.codex.codexbl.main

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import so.codex.codexbl.koin.apiModule
import so.codex.codexbl.koin.authApiModule
import so.codex.codexbl.koin.authInteractorsModule
import so.codex.codexbl.koin.interactorsModule
import so.codex.codexbl.koin.providersModule
import so.codex.core.koin.coreModule

/**
 * Class for initialized tree of dependencies.
 * @author Shiplayer
 */
class CodexKoinComponent {
    companion object {
        val globalModuleSet = setOf(
            coreModule
        )

        val loginModuleSet = setOf(
            authApiModule,
            authInteractorsModule
        )

        val authUserModulesSet = globalModuleSet + setOf(
            apiModule,
            interactorsModule,
            providersModule
        )

        private var koinApplication: KoinApplication? = KoinApplication.init()

        /**
         * For starting calculating koin graph dependencies
         * @param applicationContext Used for set up koin context
         */
        fun start(applicationContext: Context) {
            koinApplication = startKoin {
                androidLogger(Level.DEBUG)
                androidContext(applicationContext)
            }

        }

        fun updateDependencies(scope: ScopeDependencies) {
            when (scope) {
                ScopeDependencies.LOGIN_SCOPE -> {
                    //koinApplication?.unloadModules((authUserModulesSet - loginModuleSet).toList())
                    koinApplication?.modules(loginModuleSet.toList())
                }
                ScopeDependencies.MAIN_SCOPE -> {
                    koinApplication?.modules(authUserModulesSet.toList())
                }
                ScopeDependencies.GLOBAL_SCOPE -> {
                    koinApplication?.modules(globalModuleSet.toList())
                }

            }
        }
    }

    enum class ScopeDependencies {
        MAIN_SCOPE,
        LOGIN_SCOPE,
        GLOBAL_SCOPE
    }

}
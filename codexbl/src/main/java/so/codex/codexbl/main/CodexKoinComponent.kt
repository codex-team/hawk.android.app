package so.codex.codexbl.main

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import so.codex.codexbl.koin.authInteractorsModule
import so.codex.codexbl.koin.interactorsModule
import so.codex.codexbl.koin.providersModule
import so.codex.codexbl.koin.repositoriesModule
import so.codex.core.koin.coreModule
import so.codex.hawkapi.koin.authApiModule
import so.codex.hawkapi.koin.coreNetworkModule
import so.codex.hawkapi.koin.networkModule

/**
 * Class for initialized tree of dependencies.
 * @author Shiplayer
 */
class CodexKoinComponent {
    companion object {

        // Dependencies all time
        val globalModuleSet = setOf(
            coreModule,
            coreNetworkModule
        )

        // Dependencies only on login scope
        val loginModuleSet = setOf(
            authApiModule,
            authInteractorsModule
        )

        // Dependencies on Authorization scope
        val authUserModulesSet = globalModuleSet + setOf(
            networkModule,
            interactorsModule,
            providersModule,
            repositoriesModule
        )

        //
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

        /**
         * Update scope for change dependencies
         */
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

    /**
     * Constants for defining dependencies
     */
    enum class ScopeDependencies {
        // Scope that use common dependencies
        MAIN_SCOPE,

        // Scope where user can logged in or registration
        LOGIN_SCOPE,

        // Scope for dependencies that need in other scopes or need use all time
        GLOBAL_SCOPE
    }

}
package so.codex.codexbl.main

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import so.codex.codexbl.koin.apiModule
import so.codex.codexbl.koin.interactorsModule
import so.codex.codexbl.koin.providersModule

/**
 * Class for initialized tree of dependencies.
 * @author Shiplayer
 */
class CodexKoinComponent {
    companion object {
        /**
         * For starting calculating koin graph dependencies
         * @param applicationContext Used for set up koin context
         */
        fun start(applicationContext: Context) {
            startKoin {
                androidLogger()
                androidContext(applicationContext)
                modules(
                        listOf(
                                apiModule,
                                interactorsModule,
                                providersModule
                        )
                )
            }
        }
    }

}
package so.codex.codexbl.main

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import so.codex.codexbl.koin.apiModule
import so.codex.codexbl.koin.interactorsModule
import so.codex.codexbl.koin.providersModule

class StartKoinComponent {
    companion object {
        fun start(applicationContext: Context) {
            startKoin {
                androidContext(applicationContext)
                modules(listOf(
                        apiModule,
                        interactorsModule,
                        providersModule
                ))
            }
        }
    }

}
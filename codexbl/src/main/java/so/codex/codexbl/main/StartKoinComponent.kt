package so.codex.codexbl.main

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import so.codex.codexbl.koin.apiModule
import so.codex.codexbl.koin.interactorsModule
import so.codex.codexbl.koin.providersModule

/**
 * Класс для инициализации дерева зависимостей Koin. Необходиом вызывать его в Application
 * @author Shiplayer
 */
class StartKoinComponent {
    companion object {
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
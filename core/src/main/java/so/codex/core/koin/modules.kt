package so.codex.core.koin

import org.koin.dsl.module
import so.codex.core.UserTokenDAO
import so.codex.core.UserTokenPreferences

/**
 * Module that declared all dependencies for core components and classes
 */
val coreModule = module {
    single<UserTokenDAO>() {
        UserTokenPreferences(
            get()
        )
    }
}
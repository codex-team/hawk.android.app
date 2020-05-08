package so.codex.core.koin

import org.koin.dsl.module
import so.codex.core.UserTokenDAO
import so.codex.core.UserTokenPreferences

val coreModule = module {
    single<UserTokenDAO>() {
        UserTokenPreferences(
            get()
        )
    }
}
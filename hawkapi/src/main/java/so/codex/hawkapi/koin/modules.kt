package so.codex.hawkapi.koin

import org.koin.dsl.module
import so.codex.hawkapi.api.ApolloTokenInterceptor

val apiModule = module {
    single { ApolloTokenInterceptor(get()) }
}

val networkModule = module {

}
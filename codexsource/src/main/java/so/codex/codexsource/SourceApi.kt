package so.codex.codexsource

import so.codex.codexsource.base.interfaces.IAuthApi

interface SourceApi {
    fun getAuthApi(): IAuthApi
}
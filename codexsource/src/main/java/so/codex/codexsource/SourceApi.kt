package so.codex.codexsource

import so.codex.sourceinterfaces.IAuthApi

interface SourceApi {
    fun getAuthApi(): IAuthApi
}
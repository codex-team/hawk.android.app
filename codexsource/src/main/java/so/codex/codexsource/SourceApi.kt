package so.codex.codexsource

import so.codex.codexbl.output.interfaces.IAuthApi

interface SourceApi {
    fun getAuthApi(): IAuthApi
}
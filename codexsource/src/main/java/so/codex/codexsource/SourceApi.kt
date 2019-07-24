package so.codex.codexsource

import so.codex.sourceinterfaces.IAuthApi

/**
 * Интерфейс, в котором определены все методы, которые возвращают соответсвутющие компоненты для взаимодействия с сервером
 */
interface SourceApi {
    fun getAuthApi(): IAuthApi
}
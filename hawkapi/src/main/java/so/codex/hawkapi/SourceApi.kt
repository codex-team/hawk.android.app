package so.codex.hawkapi

import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.IWorkspaceApi

/**
 * Интерфейс, в котором определены все методы, которые возвращают соответсвутющие компоненты для взаимодействия с сервером
 */
interface SourceApi {
    fun getAuthApi(): IAuthApi

    fun getWorkspaceApi(): IWorkspaceApi
}
package so.codex.codexbl.interactors

import so.codex.codexbl.entity.SessionData

/**
 * Интерфейс, в котором определены методы необходимые для взаимодействия с пользовательскими данными
 * @author Shiplayer
 */
interface IUserInteractor {
    fun saveSession(session: SessionData): Boolean
    fun getLastSession(): SessionData?
    fun clear()
}
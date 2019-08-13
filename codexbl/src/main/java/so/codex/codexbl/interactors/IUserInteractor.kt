package so.codex.codexbl.interactors

import so.codex.codexbl.entity.SessionData

interface IUserInteractor {
    fun saveSession(session: SessionData): Boolean
}
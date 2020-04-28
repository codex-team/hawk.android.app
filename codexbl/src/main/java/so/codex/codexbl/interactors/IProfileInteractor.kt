package so.codex.codexbl.interactors

import io.reactivex.Single
import so.codex.codexbl.entity.Profile
import so.codex.codexbl.entity.Workspace

/**
 * Interface with declared methods for work with [Profile] and communication
 * @author YorkIsMine
 */
interface IProfileInteractor {
    /**
     * Get profile
     * @return [Single] with current Profile
     */
    fun getProfile(): Single<Profile>
}
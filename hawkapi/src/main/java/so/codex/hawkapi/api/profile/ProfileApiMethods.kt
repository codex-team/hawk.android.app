package so.codex.hawkapi.api.profile

import io.reactivex.rxjava3.core.Single
import so.codex.sourceinterfaces.response.ProfileResponse

/**
 * Declared methods for sending request only for getting information of Profile or working with it
 * @author YorkIsMine
 */
interface ProfileApiMethods {
    /**
     * Get Profile
     * @return Single with [ProfileResponse]
     */
    fun getProfile(): Single<ProfileResponse>
}
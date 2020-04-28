package so.codex.sourceinterfaces

import io.reactivex.Single
import so.codex.sourceinterfaces.response.ProfileResponse

/**
 * Interface with declared method for sending http requests. The interface is only responsible for getting information
 * of Profile.
 * @author YorkIsMine
 */
interface IProfileApi {
    /**
     * Send request for getting Profile with common information
     * @return [Single] with response of request
     */
    fun me(): Single<ProfileResponse>
}
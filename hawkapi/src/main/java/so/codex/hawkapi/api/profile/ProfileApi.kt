package so.codex.hawkapi.api.profile

import io.reactivex.rxjava3.core.Single
import so.codex.sourceinterfaces.IProfileApi
import so.codex.sourceinterfaces.response.ProfileResponse

/**
 * Implementation of interface [IProfileApi]. Class is singleton with provide method for communication with server
 * and getting information of profile via sending api request to the server.
 * @constructor Have private constructor for initialize singleton instance of class
 * @author YorkIsMine
 */
class ProfileApi internal constructor(private val service: ProfileApiMethods) : IProfileApi {

    /**
     * Method for getting Response of Profile
     * @return [Single] of current response
     */
    override fun getProfileResponse(): Single<ProfileResponse> = service.getProfile()
}
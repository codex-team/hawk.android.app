package so.codex.hawkapi.api.profile

import io.reactivex.Single
import so.codex.hawkapi.ProfileApiMethodsImpl
import so.codex.hawkapi.api.CoreApi
import so.codex.sourceinterfaces.IProfileApi
import so.codex.sourceinterfaces.response.ProfileResponse

/**
 * Implementation of interface [IProfileApi]. Class is singleton with provide method for communication with server
 * and getting information of profile via sending api request to the server.
 * @constructor Have private constructor for initialize singleton instance of class
 * @author YorkIsMine
 */
class ProfileApi(private val service: ProfileApiMethods) : IProfileApi {

    companion object {
        /**
         * Singleton of ProfileApi
         */
        val instance by lazy {
            ProfileApi(ProfileApiMethodsImpl(CoreApi.apollo))
        }
    }

    /**
     * Method for getting Response of Profile
     * @return [Single] of current response
     */
    override fun me(): Single<ProfileResponse> = service.me()
}
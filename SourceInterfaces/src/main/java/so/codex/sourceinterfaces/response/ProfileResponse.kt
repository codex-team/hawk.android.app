package so.codex.sourceinterfaces.response

import so.codex.sourceinterfaces.entity.UserEntity

/**
 * Representation of response from http request on getting information of Profile.
 * @property UserEntity Information of Profile
 * @see UserEntity
 * @author YorkIsMine
 */
data class ProfileResponse(val entity: UserEntity)
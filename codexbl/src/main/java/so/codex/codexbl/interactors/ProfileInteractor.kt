package so.codex.codexbl.interactors

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import so.codex.codexbl.entity.Profile
import so.codex.hawkapi.api.CoreApi

/**
 * Class for communication with repositories or api that provide methods for getting necessary information
 * of Profile.
 * @author YorkIsMine
 */
class ProfileInteractor : IProfileInteractor {

    /**
     * Implementation of getProfile
     * @return [Single] with current Profile
     */
    override fun getProfile(): Single<Profile> {
        return CoreApi.instance.getProfileApi().getProfileResponse().map {
            Profile(
                it.entity.id,
                it.entity.email,
                it.entity.name,
                it.entity.picture
            )

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
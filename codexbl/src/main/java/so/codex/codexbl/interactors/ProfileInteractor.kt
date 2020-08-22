package so.codex.codexbl.interactors

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import so.codex.codexbl.entity.Profile
import so.codex.sourceinterfaces.IProfileApi

/**
 * Class for communication with repositories or api that provide methods for getting necessary information
 * of Profile.
 * @author YorkIsMine
 */
class ProfileInteractor(private val profileApi: IProfileApi) : RefreshableInteractor(),
    IProfileInteractor {

    /**
     * Implementation of getProfile
     * @return [Single] with current Profile
     */
    override fun getProfile(): Single<Profile> {
        return profileApi.getProfileResponse()
            .refreshTokenSingle()
            .map {
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
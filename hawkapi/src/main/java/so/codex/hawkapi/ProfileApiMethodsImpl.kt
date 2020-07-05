package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.api.profile.ProfileApiMethods
import so.codex.sourceinterfaces.entity.UserEntity
import so.codex.sourceinterfaces.response.ProfileResponse

/**
 * Class that used [ApolloClient] for sending GraphQL request and converted response to RxJava2.
 * Implementation interface [ProfileApiMethods] for handling and converting requests and responses to RxJava2.
 * @see ProfileApiMethods
 * @author YorkIsMine
 */
class ProfileApiMethodsImpl(private val apollo: ApolloClient) :
    ProfileApiMethods, KoinComponent {

    /**
     * Use for getting access token
     */
    private val userTokenProvider by inject<UserTokenProvider>()

    /**
     * Get Profile use apollo with extensions
     * @return Single with [ProfileResponse]
     */
    override fun getProfile(): Single<ProfileResponse> {
        return apollo.retryQuery(GetCommonInformationQuery(), userTokenProvider)
            .handleHttpErrorsSingle().map {
                if (it.me != null) {
                    ProfileResponse(
                        UserEntity(
                            it.me.id,
                            it.me.email ?: "",
                            it.me.name ?: "",
                            it.me.image ?: ""
                        )
                    )
                } else {
                    ProfileResponse(UserEntity())
                }
            }

    }

}
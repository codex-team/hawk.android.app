package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import so.codex.core.UserTokenProvider
import so.codex.hawkapi.api.profile.ProfileApiMethods
import so.codex.sourceinterfaces.entity.UserEntity
import so.codex.sourceinterfaces.response.ProfileResponse
import java.util.concurrent.TimeUnit

/**
 * Class that used [ApolloClient] for sending GraphQL request and converted response to RxJava2.
 * Implementation interface [ProfileApiMethods] for handling and converting requests and responses to RxJava2.
 * @see ProfileApiMethods
 * @author YorkIsMine
 */
class ProfileApiMethodsImpl(private val apollo: ApolloClient) :
    ProfileApiMethods, KoinComponent {

    private val userTokenProvider by inject<UserTokenProvider>()


    override fun getProfile(): Single<ProfileResponse> {
        return Observable.interval(200, TimeUnit.MILLISECONDS).firstOrError().flatMap {
            apollo.retryQuery(GetCommonInformationQuery(), userTokenProvider)
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

}
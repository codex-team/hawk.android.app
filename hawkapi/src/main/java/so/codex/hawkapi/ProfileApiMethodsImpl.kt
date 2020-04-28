package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import io.reactivex.Single
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
    ProfileApiMethods {
    override fun me(): Single<ProfileResponse> {
        return apollo.rxQuery(GetCommonInformationQuery())
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
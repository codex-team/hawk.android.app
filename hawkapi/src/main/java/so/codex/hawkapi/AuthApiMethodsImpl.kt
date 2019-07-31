package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single
import so.codex.hawkapi.api.AuthApiMethods
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse
import so.codex.sourceinterfaces.response.TokenResponse

class AuthApiMethodsImpl(private val apolloClient: ApolloClient) : AuthApiMethods {
    override fun login(authEntity: AuthEntity): Single<LoginResponse> {
        return Rx2Apollo.from(
            apolloClient.mutate(
                LoginMutation.builder()
                    .email(authEntity.email)
                    .password(authEntity.password)
                    .build()
            )
        ).handleHttpErrorsSingle().map {
            LoginResponse(TokenResponse(it.login.accessToken, it.login.refreshToken))
        }
    }

    override fun signUp(signUpEntity: SignUpEntity): Single<SignUpResponse> {
        return Rx2Apollo.from(
            apolloClient.mutate(
                SignUpMutation.builder()
                    .email(signUpEntity.email)
                    .build()
            )
        ).handleHttpErrorsSingle().map {
            SignUpResponse(it.signUp)
        }
    }

}
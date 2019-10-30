package so.codex.hawkapi

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single
import so.codex.hawkapi.api.auth.AuthApiMethods
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Class that used [ApolloClient] for sending GraphQL request and converted response to RxJava2.
 * Implementation interface [AuthApiMethods] for handling and converting requests and responses to RxJava2.
 * @see AuthApiMethods
 * @author Shiplayer
 */
class AuthApiMethodsImpl(private val apolloClient: ApolloClient) : AuthApiMethods {

    /**
     * Use [handleHttpErrorsSingle] checking errors in message and converting from Response to mutation data of Login.
     */
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

    /**
     * Use [handleHttpErrorsSingle] checking errors in message and converting from Response to mutation data of SignUp.
     */
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

    /**
     * Use [handleHttpErrorsSingle] checking errors in message and converting from Response to mutation data of Token.
     */
    override fun refreshToken(token: TokenEntity): Single<TokenResponse> {
        return Rx2Apollo.from(
            apolloClient.mutate(
                RefreshTokensMutation(token.refreshToken)
            )
        ).handleHttpErrorsSingle().map {
            TokenResponse(it.refreshTokens.accessToken, it.refreshTokens.refreshToken)
        }
    }

}
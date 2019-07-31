package so.codex.codexsource

import com.apollographql.apollo.ApolloClient
import io.reactivex.Single
import so.codex.codexsource.api.AuthApiMethods
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.RequestEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.response.CodexResponse
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse

class AuthApiMethodsImpl(val apolloClient: ApolloClient): AuthApiMethods{
    override fun login(authEntity: AuthEntity): Single<CodexResponse<LoginResponse>> {
        apolloClient.mutate(LoginMutation.builder()
                .email(authEntity.email)
                .password(authEntity.password)
                .build())
    }

    override fun signUp(signUpEntity: SignUpEntity): Single<CodexResponse<SignUpResponse>> {
        apolloClient.mutate()
    }

}
package so.codex.codexsource.api

import io.reactivex.Single
import retrofit2.http.GET
import so.codex.codexsource.base.AuthEntity
import so.codex.codexsource.response.TokenResponse

interface AuthApiMethods {
    @GET("/graphql")
    fun login(authEntity: AuthEntity): Single<TokenResponse>
}
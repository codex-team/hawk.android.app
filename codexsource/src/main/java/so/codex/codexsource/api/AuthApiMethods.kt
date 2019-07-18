package so.codex.codexsource.api

import io.reactivex.Single
import retrofit2.http.GET
import so.codex.codexbl.output.entity.AuthEntity
import so.codex.codexbl.output.response.TokenResponse

interface AuthApiMethods {
    @GET("/graphql")
    fun login(authEntity: AuthEntity): Single<TokenResponse>
}
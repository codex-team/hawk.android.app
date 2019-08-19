package so.codex.hawkapi.api.auth

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Интерфейс, в котором определяются все методы для взаимодействия с API
 */
interface AuthApiMethods {
    @POST("/graphql")
    fun login(@Body authEntity: AuthEntity): Single<LoginResponse>

    @POST("/graphql")
    fun signUp(@Body signUpEntity: SignUpEntity): Single<SignUpResponse>

    @POST("/graphql")
    fun refreshToken(@Body token: TokenEntity): Single<TokenResponse>
}
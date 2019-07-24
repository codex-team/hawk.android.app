package so.codex.codexsource.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import so.codex.sourceinterfaces.entity.RequestEntity
import so.codex.sourceinterfaces.response.CodexResponse
import so.codex.sourceinterfaces.response.LoginResponse
import so.codex.sourceinterfaces.response.SignUpResponse

/**
 * Интерфейс, в котором определяются все методы для взаимодействия с API
 */
interface AuthApiMethods {
    @POST("/graphql")
    fun login(@Body requestEntity: RequestEntity): Single<CodexResponse<LoginResponse>>

    @POST("/graphql")
    fun signUp(@Body requestEntity: RequestEntity): Single<CodexResponse<SignUpResponse>>
}
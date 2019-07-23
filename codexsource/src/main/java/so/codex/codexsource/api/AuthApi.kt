package so.codex.codexsource.api

import so.codex.sourceinterfaces.IAuthApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.response.TokenResponse

final class AuthApi private constructor(val service: AuthApiMethods) : IAuthApi {
    companion object {
        val instance by lazy {
            AuthApi(CoreApi.retrofit.create(AuthApiMethods::class.java))
        }
    }

    override fun login(auth: AuthEntity): Single<TokenResponse> = service.login(auth).subscribeOn(Schedulers.io())

}
package so.codex.codexsource.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import so.codex.codexsource.base.AuthEntity
import so.codex.codexsource.base.interfaces.IAuthApi
import so.codex.codexsource.response.TokenResponse

final class AuthApi private constructor(val service: AuthApiMethods) : IAuthApi {
    companion object {
        val instance by lazy {
            AuthApi(CoreApi.retrofit.create(AuthApiMethods::class.java))
        }
    }

    override fun login(auth: AuthEntity): Single<TokenResponse> = service.login(auth).subscribeOn(Schedulers.io())

}
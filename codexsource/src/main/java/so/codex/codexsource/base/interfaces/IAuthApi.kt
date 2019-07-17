package so.codex.codexsource.base.interfaces

import io.reactivex.Single
import so.codex.codexsource.base.AuthEntity
import so.codex.codexsource.response.TokenResponse

interface IAuthApi {
    fun login(auth: AuthEntity): Single<TokenResponse>
}
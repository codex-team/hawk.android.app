package so.codex.codexbl.output.interfaces

import io.reactivex.Single
import so.codex.codexbl.output.entity.AuthEntity
import so.codex.codexbl.output.response.TokenResponse

interface IAuthApi {
    fun login(auth: AuthEntity): Single<TokenResponse>
}
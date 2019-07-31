package so.codex.sourceinterfaces

import io.reactivex.Single
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.response.TokenResponse

interface IAuthApi {
    fun login(auth: AuthEntity): Single<TokenResponse>

    fun signUp(signUp: SignUpEntity): Single<Boolean>
}
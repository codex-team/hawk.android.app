package so.codex.hawkapi.api.auth

import io.reactivex.Single
import so.codex.hawkapi.AuthApiMethodsImpl
import so.codex.hawkapi.api.CoreApi
import so.codex.hawkapi.api.TokenInterceptor
import so.codex.hawkapi.subscribeOnIO
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.entity.TokenEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Данный класс является singleton, в котором определена логика того, как взаимодействовать с сервером, во что завернуть объект и какой отдать.
 * Реализует интерфейс [IAuthApi]
 */
class AuthApi private constructor(private val service: AuthApiMethods) : IAuthApi {

    companion object {
        val instance by lazy {
            AuthApi(AuthApiMethodsImpl(CoreApi.apollo))
        }
    }

    /**
     * Метод, который отправляет запрос на авторизацию
     * @param auth - принимает объект [AuthEntity], который конвертирует в объект для graphql
     * @return возвращает [TokenResponse], в котором уже есть токен и рефреш токен
     */
    override fun login(auth: AuthEntity): Single<TokenResponse> =
            service.login(auth)
                .subscribeOnIO().map { it.login }

    override fun signUp(signUp: SignUpEntity): Single<Boolean> =
            service.signUp(signUp)
                .subscribeOnIO().map { it.signUp }

    override fun refreshToken(token: TokenEntity): Single<TokenResponse> =
            service.refreshToken(token)
                .subscribeOnIO()
                    .doOnSuccess {
                        TokenInterceptor.instance.updateToken(it.accessToken)
                    }

}
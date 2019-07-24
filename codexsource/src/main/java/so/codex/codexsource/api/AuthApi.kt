package so.codex.codexsource.api

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import so.codex.sourceinterfaces.IAuthApi
import so.codex.sourceinterfaces.entity.AuthEntity
import so.codex.sourceinterfaces.entity.RequestEntity
import so.codex.sourceinterfaces.entity.SignUpEntity
import so.codex.sourceinterfaces.response.TokenResponse

/**
 * Данный класс является singleton, в котором определена логика того, как взаимодействовать с сервером, во что завернуть объект и какой отдать.
 * Реализует интерфейс [IAuthApi]
 */
final class AuthApi private constructor(private val service: AuthApiMethods) : IAuthApi {
    companion object {
        val instance by lazy {
            AuthApi(CoreApi.retrofit.create(AuthApiMethods::class.java))
        }
    }

    /**
     * Метод, который отправляет запрос на авторизацию
     * @param auth - принимает объект [AuthEntity], который конвертирует в объект для graphql
     * @return возвращает [TokenResponse], в котором уже есть токен и рефреш токен
     */
    override fun login(auth: AuthEntity): Single<TokenResponse> =
        service.login(RequestEntity(auth.getMutation(), auth))
            .subscribeOn(Schedulers.io()).map { it.data.login }

    override fun signUp(signUp: SignUpEntity): Single<Boolean> =
            service.signUp(RequestEntity(signUp.getMutation(), signUp))
                .subscribeOn(Schedulers.io()).map { it.data.signUp }

}
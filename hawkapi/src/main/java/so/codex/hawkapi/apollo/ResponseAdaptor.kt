package so.codex.hawkapi.apollo

import com.apollographql.apollo.api.Response
import so.codex.core.entity.UserToken

class ResponseAdaptor<T>(val response: Response<T>, val requestToken: UserToken? = null) {
}
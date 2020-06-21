package so.codex.codexbl.base

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import so.codex.core.entity.UserToken

class RefreshObservableCompose : ObservableTransformer<UserToken, UserToken> {
    private var lastAccessToken = ""

    override fun apply(upstream: Observable<UserToken>): ObservableSource<UserToken> {
        return upstream.filter {
            it.accessToken == lastAccessToken
        }
    }
}
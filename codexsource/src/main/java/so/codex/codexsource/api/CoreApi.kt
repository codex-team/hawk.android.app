package so.codex.codexsource.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import so.codex.codexsource.SourceApi
import so.codex.codexsource.base.interfaces.IAuthApi

class CoreApi private constructor() : SourceApi {
    companion object {
        private val baseUrl = "https://api.stage.hawk.so/graphql"
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    override fun getAuthApi(): IAuthApi = AuthApi.instance
}
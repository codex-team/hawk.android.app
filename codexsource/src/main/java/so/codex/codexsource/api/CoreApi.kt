package so.codex.codexsource.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import so.codex.codexsource.SourceApi
import so.codex.sourceinterfaces.IAuthApi

class CoreApi private constructor() : SourceApi {
    companion object {
        private val baseUrl = "https://api.stage.hawk.so/graphql"
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        public val instance by lazy {
            CoreApi()
        }
    }

    override fun getAuthApi(): IAuthApi = AuthApi.instance
}
package so.codex.codexsource

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * В данный момент не используется
 * TODO написать свой конвертр для запросов graphql
 */
class MoshiFactory private constructor() : Converter.Factory() {
    companion object {
        val factory by lazy {
            MoshiFactory()
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun toJson(writer: JsonWriter, value: Any?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}
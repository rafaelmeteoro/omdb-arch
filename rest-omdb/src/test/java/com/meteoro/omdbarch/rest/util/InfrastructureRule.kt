package com.meteoro.omdbarch.rest.util

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.meteoro.omdbarch.rest.api.OmdbAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

internal class InfrastructureRule : ExternalResource() {

    lateinit var server: MockWebServer
    lateinit var api: OmdbAPI

    override fun before() {
        super.before()
        server = MockWebServer()
        val url = server.url("/").toString()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        val gsonBuilder = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(
                Date::class.java,
                JsonDeserializer { json, _, _ -> Date(json.asJsonPrimitive.asLong) }
            )

        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .client(client)
            .build()

        api = retrofit.create(OmdbAPI::class.java)
    }

    override fun after() {
        server.shutdown()
        super.after()
    }

    fun defineScenario(status: Int, response: String = "") {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(status)
                setBody(response)
            }
        )
    }
}
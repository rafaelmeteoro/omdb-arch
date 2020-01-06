package com.meteoro.omdbarch.rest.util

import com.meteoro.omdbarch.rest.BuildRetrofit
import com.meteoro.omdbarch.rest.api.OmdbAPI
import kotlinx.serialization.UnstableDefault
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

internal class InfrastructureRule : ExternalResource() {

    private lateinit var server: MockWebServer
    lateinit var api: OmdbAPI

    @UnstableDefault
    override fun before() {
        super.before()
        server = MockWebServer()
        val url = server.url("/").toString()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        api = BuildRetrofit(url, client).create(OmdbAPI::class.java)
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
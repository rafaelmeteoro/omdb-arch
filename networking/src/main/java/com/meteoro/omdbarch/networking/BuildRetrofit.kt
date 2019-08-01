package com.meteoro.omdbarch.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object BuildRetrofit {

    const val API_URL = "http://www.omdbapi.com/"
    const val API_KEY = "apikey"
    const val API_KEY_VALUE = "1abc75a6"

    @UnstableDefault
    operator fun invoke(apiURL: String, httpClient: OkHttpClient) =
        with(Retrofit.Builder()) {
            baseUrl(apiURL)
            client(httpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            build()
        }

    private val contentType by lazy {
        "application/json".toMediaTypeOrNull()!!
    }
}
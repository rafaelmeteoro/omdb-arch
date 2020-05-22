package com.meteoro.omdbarch.rest

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object BuildRetrofit {

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

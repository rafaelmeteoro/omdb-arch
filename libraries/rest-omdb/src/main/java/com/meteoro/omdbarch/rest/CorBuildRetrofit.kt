package com.meteoro.omdbarch.rest

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object CorBuildRetrofit {

    @OptIn(ExperimentalSerializationApi::class)
    operator fun invoke(apiURL: String, httpClient: OkHttpClient) =
        with(Retrofit.Builder()) {
            baseUrl(apiURL)
            client(httpClient)
            addConverterFactory(Json.asConverterFactory(contentType))
            build()
        }

    private val contentType by lazy {
        "application/json".toMediaTypeOrNull()!!
    }
}

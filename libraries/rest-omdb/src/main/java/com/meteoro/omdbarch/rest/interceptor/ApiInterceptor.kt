package com.meteoro.omdbarch.rest.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiKeyValue: String) : Interceptor {

    companion object {
        private const val API_KEY = "apikey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, apiKeyValue)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
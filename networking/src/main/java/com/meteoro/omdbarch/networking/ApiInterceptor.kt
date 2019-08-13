package com.meteoro.omdbarch.networking

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiKey: String, private val apiKeyValue: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder().addQueryParameter(apiKey, apiKeyValue).build()
        val request = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}
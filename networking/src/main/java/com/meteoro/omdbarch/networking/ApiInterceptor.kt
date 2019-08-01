package com.meteoro.omdbarch.networking

import com.meteoro.omdbarch.networking.BuildRetrofit.API_KEY
import com.meteoro.omdbarch.networking.BuildRetrofit.API_KEY_VALUE
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder().addQueryParameter(API_KEY, API_KEY_VALUE).build()
        val request = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}
package com.meteoro.omdbarch.rest.interceptor

import com.meteoro.omdbarch.domain.errors.NoConnectivityError
import com.meteoro.omdbarch.domain.services.ConnectivityService
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val service: ConnectivityService) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!service.isConnected()) {
            throw NoConnectivityError
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
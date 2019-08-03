package com.meteoro.omdbarch.networking.di

import com.meteoro.omdbarch.networking.ApiInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeUnit

val networkingModule = Kodein.Module("networking") {

    val timeout = 60L

    val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    bind() from singleton {
        OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .addInterceptor(ApiInterceptor())
            .build()
    }
}
package com.meteoro.omdbarch.networking.di

import com.meteoro.omdbarch.networking.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        private const val TIME_OUT = 60L
    }

    @Provides
    @Reusable
    internal fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .addInterceptor(ApiInterceptor())
            .build()
    }
}
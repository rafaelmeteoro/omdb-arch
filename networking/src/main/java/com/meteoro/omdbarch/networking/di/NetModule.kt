package com.meteoro.omdbarch.networking.di

import com.meteoro.omdbarch.networking.ApiInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule(private val apiKeyValue: String, private val isDebugMode: Boolean) {

    companion object {
        private const val TIMEOUT = 60L
    }

    @Provides
    @Singleton
    fun providerHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (isDebugMode) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ApiInterceptor(apiKeyValue))
            .addInterceptor(logger)
            .build()
}
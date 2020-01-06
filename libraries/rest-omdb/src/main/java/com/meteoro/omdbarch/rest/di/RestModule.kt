package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.rest.*
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.interceptor.ApiInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.UnstableDefault
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@UnstableDefault
@Module
class RestModule(private val apiUrl: String, private val apiKeyValue: String) {

    companion object {
        private const val TIMEOUT = 60L
    }

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        logger: HttpLoggingInterceptor,
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ApiInterceptor(apiKeyValue))
            .addInterceptor(logger)

        interceptors.forEach { builder.addNetworkInterceptor(it) }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): OmdbAPI {
        val retrofit = BuildRetrofit(
            apiURL = apiUrl,
            httpClient = client
        )

        return retrofit.create(OmdbAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieService(api: OmdbAPI, logger: Logger): MovieService =
        MovieInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            ),
            targetScheduler = Schedulers.io()
        )

    @Provides
    @Singleton
    fun provideSearchService(api: OmdbAPI, logger: Logger): SearchService =
        SearchInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            ),
            targetScheduler = Schedulers.io()
        )
}
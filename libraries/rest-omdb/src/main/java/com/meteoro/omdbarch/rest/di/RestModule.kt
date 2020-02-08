package com.meteoro.omdbarch.rest.di

import android.app.Application
import com.meteoro.omdbarch.domain.services.ConnectivityService
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.rest.*
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.interceptor.ApiInterceptor
import com.meteoro.omdbarch.rest.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.UnstableDefault
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@UnstableDefault
@Module
class RestModule(private val apiUrl: String, private val apiKeyValue: String) {

    companion object {
        private const val TIMEOUT = 60L
        private const val FOLDER_CACHE_NAME = "http-cache"
        private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
    }

    @Provides
    @Singleton
    fun provideConnectManager(application: Application): ConnectivityService {
        return ConnectManager.Builder(application).build()
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
    fun provideCache(application: Application): Cache {
        val cacheSize = CACHE_SIZE.toLong()
        val httpCacheDirectory = File(application.cacheDir, FOLDER_CACHE_NAME)
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        service: ConnectivityService,
        cache: Cache,
        logger: HttpLoggingInterceptor,
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(service))
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
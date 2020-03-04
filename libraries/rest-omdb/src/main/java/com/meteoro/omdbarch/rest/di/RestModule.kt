package com.meteoro.omdbarch.rest.di

import android.app.Application
import com.meteoro.omdbarch.domain.services.ConnectivityService
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.rest.*
import com.meteoro.omdbarch.rest.api.OmdbAPI
import com.meteoro.omdbarch.rest.executor.RemoteExecutor
import com.meteoro.omdbarch.rest.executor.RemoteExecutorImpl
import com.meteoro.omdbarch.rest.interceptor.ApiInterceptor
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
        return NetworkHandler(application)
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
    fun provideExecutor(service: ConnectivityService): RemoteExecutor =
        RemoteExecutorImpl(
            service = service,
            targetScheduler = Schedulers.io()
        )

    @Provides
    @Singleton
    fun provideMovieService(api: OmdbAPI, executor: RemoteExecutor, logger: Logger): MovieService =
        MovieInfrastructure(
            service = api,
            executor = executor,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            )
        )

    @Provides
    @Singleton
    fun provideSearchService(api: OmdbAPI, executor: RemoteExecutor, logger: Logger): SearchService =
        SearchInfrastructure(
            service = api,
            executor = executor,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            )
        )
}
package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.networking.BuildRetrofit
import com.meteoro.omdbarch.rest.ExecutionErrorHandler
import com.meteoro.omdbarch.rest.ExecutionErrorHandlerFlowable
import com.meteoro.omdbarch.rest.MovieInfrastructure
import com.meteoro.omdbarch.rest.SearchInfrastructure
import com.meteoro.omdbarch.rest.api.OmdbAPI
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.UnstableDefault
import okhttp3.OkHttpClient
import javax.inject.Singleton

@UnstableDefault
@Module
class RestModule(private val apiUrl: String) {

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
            errorHandlerFlowable = ExecutionErrorHandlerFlowable(
                logger = logger
            ),
            targetScheduler = Schedulers.io()
        )
}
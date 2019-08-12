package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.networking.BuildRetrofit
import com.meteoro.omdbarch.rest.ExecutionErrorHandler
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
class RestModule {

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): OmdbAPI {
        val retrofit = BuildRetrofit(
            apiURL = OmdbAPI.API_URL,
            httpClient = client
        )

        return retrofit.create(OmdbAPI::class.java)
    }

    @Provides
    fun provideMovieService(api: OmdbAPI, logger: Logger): MovieService =
        MovieInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            ),
            targetScheduler = Schedulers.io()
        )

    @Provides
    fun provideSearchService(api: OmdbAPI, logger: Logger): SearchService =
        SearchInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = logger
            ),
            targetScheduler = Schedulers.io()
        )
}
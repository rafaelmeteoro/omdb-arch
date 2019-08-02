package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.ConsoleLogger
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

@Module
class RestModule {

    @UnstableDefault
    @Provides
    fun provideOmdbAPI(okHttpClient: OkHttpClient): OmdbAPI {
        val retrofit = BuildRetrofit(
            apiURL = OmdbAPI.API_URL,
            httpClient = okHttpClient
        )

        return retrofit.create(OmdbAPI::class.java)
    }

    @Provides
    fun provideMovieInfrastructure(api: OmdbAPI): MovieService {
        return MovieInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = ConsoleLogger
            ),
            targetScheduler = Schedulers.io()
        )
    }

    @Provides
    fun provideSearchInfrastructure(api: OmdbAPI, instance: Logger): SearchService {
        return SearchInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = instance
            ),
            targetScheduler = Schedulers.io()
        )
    }
}
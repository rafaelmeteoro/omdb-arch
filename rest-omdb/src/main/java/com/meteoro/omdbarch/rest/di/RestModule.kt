package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.networking.BuildRetrofit
import com.meteoro.omdbarch.networking.di.NetworkComponent
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

@Module(subcomponents = [NetworkComponent::class])
class RestModule(private val instance: Logger) {

    @UnstableDefault
    @Singleton
    @Provides
    fun provideOmdbAPI(okHttpClient: OkHttpClient): OmdbAPI {
        val retrofit = BuildRetrofit(
            apiURL = OmdbAPI.API_URL,
            httpClient = okHttpClient
        )

        return retrofit.create(OmdbAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieInfrastructure(api: OmdbAPI): MovieService {
        return MovieInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = instance
            ),
            targetScheduler = Schedulers.io()
        )
    }

    @Singleton
    @Provides
    fun provideSearchInfrastructure(api: OmdbAPI): SearchService {
        return SearchInfrastructure(
            service = api,
            errorHandler = ExecutionErrorHandler(
                logger = instance
            ),
            targetScheduler = Schedulers.io()
        )
    }
}
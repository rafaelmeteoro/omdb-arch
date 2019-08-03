package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.networking.BuildRetrofit
import com.meteoro.omdbarch.rest.ExecutionErrorHandler
import com.meteoro.omdbarch.rest.MovieInfrastructure
import com.meteoro.omdbarch.rest.SearchInfrastructure
import com.meteoro.omdbarch.rest.api.OmdbAPI
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.UnstableDefault
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

@UnstableDefault
val restingModule = Kodein.Module("rest-omdb") {

    bind() from singleton {
        val retrofit = BuildRetrofit(
            apiURL = OmdbAPI.API_URL,
            httpClient = instance()
        )

        retrofit.create(OmdbAPI::class.java)
    }

    bind<MovieService>() with provider {
        MovieInfrastructure(
            service = instance(),
            errorHandler = ExecutionErrorHandler(
                logger = instance()
            ),
            targetScheduler = Schedulers.io()
        )
    }

    bind<SearchService>() with provider {
        SearchInfrastructure(
            service = instance(),
            errorHandler = ExecutionErrorHandler(
                logger = instance()
            ),
            targetScheduler = Schedulers.io()
        )
    }
}
package com.meteoro.omdbarch.features.details.di

import android.content.Context
import com.meteoro.omdbarch.common.ResourceProvider
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.features.details.DetailViewModel
import com.meteoro.omdbarch.features.details.MovieDetailActivity
import com.meteoro.omdbarch.utilities.StateMachine
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Scheduler

@Module
abstract class DetailsModule {

    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity(): MovieDetailActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideResourceProvider(context: Context): ResourceProvider =
            ResourceProvider(context.applicationContext)

        @JvmStatic
        @Provides
        fun provideFetchMovie(service: MovieService): FetchMovie =
            FetchMovie(service)

        @JvmStatic
        @Provides
        fun provideDetailsViewModel(fetch: FetchMovie, scheduler: Scheduler, provider: ResourceProvider) =
            DetailViewModel(
                fetch = fetch,
                machine = StateMachine(
                    uiScheduler = scheduler
                ),
                resProvider = provider
            )
    }
}
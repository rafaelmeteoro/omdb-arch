package com.meteoro.omdbarch.details.di

import android.content.Context
import com.meteoro.omdbarch.details.DetailViewModel
import com.meteoro.omdbarch.details.MovieDetailActivity
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.utilities.ResourceProvider
import com.meteoro.omdbarch.utilities.StateMachine
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Scheduler
import javax.inject.Singleton

@Module
abstract class DetailsModule {

    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity(): MovieDetailActivity

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideResourceProvider(context: Context): ResourceProvider =
            ResourceProvider(context.applicationContext)

        @JvmStatic
        @Provides
        @Singleton
        fun provideFetchMovie(service: MovieService): FetchMovie =
            FetchMovie(service)

        @JvmStatic
        @Provides
        @Singleton
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
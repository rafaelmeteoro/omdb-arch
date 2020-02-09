package com.meteoro.omdbarch.details.di

import android.content.Context
import com.meteoro.omdbarch.components.ResourceProvider
import com.meteoro.omdbarch.components.StateMachine
import com.meteoro.omdbarch.details.DetailViewModel
import com.meteoro.omdbarch.details.DetailViewModelContract
import com.meteoro.omdbarch.details.MovieDetailActivity
import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.services.MovieService
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
        fun provideDetailsViewModel(
            fetch: FetchMovie,
            cache: CacheMovie,
            scheduler: Scheduler,
            provider: ResourceProvider
        ): DetailViewModelContract = DetailViewModel(
            fetch = fetch,
            cache = cache,
            machine = StateMachine(
                uiScheduler = scheduler
            ),
            resProvider = provider
        )
    }
}
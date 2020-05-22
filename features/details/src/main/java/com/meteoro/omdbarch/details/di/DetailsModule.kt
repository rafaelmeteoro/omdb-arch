package com.meteoro.omdbarch.details.di

import android.content.Context
import com.meteoro.omdbarch.components.ResourceProvider
import com.meteoro.omdbarch.details.DetailViewModel
import com.meteoro.omdbarch.details.DetailViewModelContract
import com.meteoro.omdbarch.details.MovieDetailActivity
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.MovieRepository
import com.meteoro.omdbarch.domain.state.StateMachine
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
        fun provideDetailsViewModel(
            fetch: MovieRepository,
            cache: CacheRepository,
            scheduler: Scheduler,
            provider: ResourceProvider
        ): DetailViewModelContract = DetailViewModel(
            movieRepository = fetch,
            cacheRepository = cache,
            machine = StateMachine(
                uiScheduler = scheduler
            ),
            resProvider = provider
        )
    }
}

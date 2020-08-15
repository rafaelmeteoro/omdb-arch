package com.meteoro.omdbarch.domain.di

import com.meteoro.omdbarch.domain.*
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.model.TypeDatabase
import com.meteoro.omdbarch.domain.repository.*
import com.meteoro.omdbarch.domain.services.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun providerDisposer(): Disposer = Disposer()

    @Provides
    @Singleton
    fun provideCacheRepository(@Named(TypeDatabase.ROOM) service: MovieCacheService): CacheRepository =
        CacheMovie(service)

    @Provides
    @Singleton
    fun provideMovieRepository(service: MovieService): MovieRepository =
        FetchMovie(service)

    @Provides
    @Singleton
    fun provideSearchRepository(service: SearchService): SearchRepository =
        FetchSearch(service)

    @Provides
    @Singleton
    fun provideManagerRepository(service: SearchHistoryService): ManagerRepository =
        ManagerSearch(service)

    @Provides
    @Singleton
    fun provideCorSearchRepository(service: CorSearchService): CorSearchRepository =
        CorFetchSearch(service)
}

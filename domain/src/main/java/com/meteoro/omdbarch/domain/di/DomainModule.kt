package com.meteoro.omdbarch.domain.di

import com.meteoro.omdbarch.domain.CacheMovie
import com.meteoro.omdbarch.domain.CorFetchSearch
import com.meteoro.omdbarch.domain.FetchMovie
import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.ManagerSearch
import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.domain.model.TypeDatabase
import com.meteoro.omdbarch.domain.repository.CacheRepository
import com.meteoro.omdbarch.domain.repository.CorSearchRepository
import com.meteoro.omdbarch.domain.repository.ManagerRepository
import com.meteoro.omdbarch.domain.repository.MovieRepository
import com.meteoro.omdbarch.domain.repository.SearchRepository
import com.meteoro.omdbarch.domain.services.CorSearchService
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.domain.services.MovieService
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import com.meteoro.omdbarch.domain.services.SearchService
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

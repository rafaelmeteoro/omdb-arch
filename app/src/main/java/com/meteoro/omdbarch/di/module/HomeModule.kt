package com.meteoro.omdbarch.di.module

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.services.SearchService
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideFetchSearch(instance: SearchService) = FetchSearch(service = instance)
}
package com.meteoro.omdbarch.di.module

import com.meteoro.omdbarch.domain.FetchSearch
import com.meteoro.omdbarch.domain.services.SearchService
import com.meteoro.omdbarch.rest.di.RestComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [RestComponent::class])
class HomeModule {

    @Provides
    fun provideFetchSearch(instance: SearchService) = FetchSearch(service = instance)
}
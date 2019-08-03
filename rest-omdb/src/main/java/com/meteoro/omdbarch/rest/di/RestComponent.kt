package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.SearchService
import dagger.Subcomponent

@Subcomponent(modules = [RestModule::class])
interface RestComponent {

    fun searchService(): SearchService

    @Subcomponent.Builder
    interface Builder {
        fun restBuilder(module: RestModule): Builder
        fun build(): RestComponent
    }
}
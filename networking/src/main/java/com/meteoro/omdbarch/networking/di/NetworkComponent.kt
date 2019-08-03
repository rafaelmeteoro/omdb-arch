package com.meteoro.omdbarch.networking.di

import dagger.Subcomponent

@Subcomponent(modules = [NetworkModule::class])
interface NetworkComponent {

    @Subcomponent.Builder
    interface Builder {
        fun networkBuilder(module: NetworkModule): Builder
        fun build(): NetworkComponent
    }
}
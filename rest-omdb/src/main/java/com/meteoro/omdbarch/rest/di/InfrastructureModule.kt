package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.domain.services.RemoteOmdbService
import com.meteoro.omdbarch.rest.MoveisInfrastructure
import com.meteoro.omdbarch.rest.api.OmdbAPI
import dagger.Module
import dagger.Provides

@Module
class InfrastructureModule {

    @Provides
    fun provideInfrastructure(api: OmdbAPI): RemoteOmdbService {
        return MoveisInfrastructure(api)
    }
}
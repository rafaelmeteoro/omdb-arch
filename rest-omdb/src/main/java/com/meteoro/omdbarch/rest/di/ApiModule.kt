package com.meteoro.omdbarch.rest.di

import com.meteoro.omdbarch.rest.api.OmdbAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideOmdbAPI(retrofit: Retrofit): OmdbAPI {
        return retrofit.create(OmdbAPI::class.java)
    }
}
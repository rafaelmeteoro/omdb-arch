package com.meteoro.omdbarch.di.component

import com.meteoro.omdbarch.di.module.AppModule
import com.meteoro.omdbarch.di.module.HomeModule
import com.meteoro.omdbarch.di.module.ViewModelModule
import com.meteoro.omdbarch.features.home.HomeActivity
import com.meteoro.omdbarch.networking.di.NetworkModule
import com.meteoro.omdbarch.rest.di.RestModule
import com.meteoro.omdbarch.utilities.di.SharedUtilitiesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        AppModule::class,
        NetworkModule::class,
        RestModule::class,
        SharedUtilitiesModule::class,
        HomeModule::class
    ]
)
interface OmdbComponent {
    fun inject(activity: HomeActivity)
}
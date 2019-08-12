package com.meteoro.omdbarch.di

import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.features.details.di.DetailsModule
import com.meteoro.omdbarch.features.home.di.HomeModule
import com.meteoro.omdbarch.networking.di.NetModule
import com.meteoro.omdbarch.rest.di.RestModule
import com.meteoro.omdbarch.utilities.di.ShareUtilsModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.serialization.UnstableDefault
import javax.inject.Singleton

@UnstableDefault
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ShareUtilsModule::class,
        NetModule::class,
        RestModule::class,
        HomeModule::class,
        DetailsModule::class
    ]
)
interface AppComponent : AndroidInjector<OmdbApplication>

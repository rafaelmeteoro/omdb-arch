package com.meteoro.omdbarch

import android.app.Application
import com.meteoro.omdbarch.di.component.DaggerOmdbComponent
import com.meteoro.omdbarch.di.component.OmdbComponent
import com.meteoro.omdbarch.di.module.AppModule
import com.meteoro.omdbarch.di.module.HomeModule
import com.meteoro.omdbarch.networking.di.NetworkModule
import com.meteoro.omdbarch.rest.di.RestModule
import com.meteoro.omdbarch.utilities.di.SharedUtilitiesModule

class OmdbApplication : Application() {

    companion object {
        lateinit var appCompoent: OmdbComponent
    }

    override fun onCreate() {
        super.onCreate()

        appCompoent = DaggerOmdbComponent
            .builder()
            .appModule(AppModule())
            .networkModule(NetworkModule())
            .restModule(RestModule())
            .sharedUtilitiesModule(SharedUtilitiesModule())
            .homeModule(HomeModule())
            .build()
    }
}
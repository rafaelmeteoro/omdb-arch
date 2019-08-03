package com.meteoro.omdbarch

import android.app.Application
import com.meteoro.omdbarch.di.component.DaggerOmdbComponent
import com.meteoro.omdbarch.di.component.OmdbComponent
import com.meteoro.omdbarch.di.module.AppModule
import com.meteoro.omdbarch.di.module.HomeModule
import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.networking.di.NetworkModule

class OmdbApplication : Application() {

    companion object {
        lateinit var appCompoent: OmdbComponent
    }

    override fun onCreate() {
        super.onCreate()

        val logger = ConsoleLogger

        appCompoent = DaggerOmdbComponent
            .builder()
            .appModule(AppModule())
            .networkModule(NetworkModule())
            .homeModule(HomeModule())
            .build()
    }
}
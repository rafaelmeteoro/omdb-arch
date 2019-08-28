package com.meteoro.omdbarch.di

import android.app.Application
import android.content.Context
import com.meteoro.omdbarch.logger.LogcatLogger
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.navigation.OmdbNavigator
import com.meteoro.omdbarch.navigator.MyNavigator
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Cache
import java.io.File
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private const val FOLDER_CACHE_NAME = "http-cache"
        private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideLogger(): Logger = LogcatLogger

    @Provides
    @Singleton
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = CACHE_SIZE.toLong()
        val httpCacheDirectory = File(application.cacheDir, FOLDER_CACHE_NAME)
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    fun provideNavigator(): MyNavigator = OmdbNavigator()
}
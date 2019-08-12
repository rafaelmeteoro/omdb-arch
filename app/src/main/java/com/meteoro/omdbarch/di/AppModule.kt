package com.meteoro.omdbarch.di

import android.app.Application
import android.content.Context
import com.meteoro.omdbarch.logger.LogcatLogger
import com.meteoro.omdbarch.logger.Logger
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideLogger(): Logger = LogcatLogger

    @Provides
    @Singleton
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
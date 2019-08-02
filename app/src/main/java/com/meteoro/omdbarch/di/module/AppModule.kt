package com.meteoro.omdbarch.di.module

import com.meteoro.omdbarch.logger.ConsoleLogger
import com.meteoro.omdbarch.logger.Logger
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class AppModule {

    @Provides
    fun provideLogger(): Logger {
        return ConsoleLogger
    }

    @Provides
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
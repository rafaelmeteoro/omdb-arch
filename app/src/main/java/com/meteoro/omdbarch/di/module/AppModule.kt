package com.meteoro.omdbarch.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class AppModule {

    @Provides
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
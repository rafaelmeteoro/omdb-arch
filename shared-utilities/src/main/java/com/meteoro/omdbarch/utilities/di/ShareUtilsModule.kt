package com.meteoro.omdbarch.utilities.di

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ShareUtilsModule {

    @Provides
    @Singleton
    fun provideDisposer(logger: Logger): Disposer = Disposer(logger)
}
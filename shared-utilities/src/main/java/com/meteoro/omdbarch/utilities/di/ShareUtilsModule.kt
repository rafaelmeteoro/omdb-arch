package com.meteoro.omdbarch.utilities.di

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import dagger.Module
import dagger.Provides

@Module
class ShareUtilsModule {

    @Provides
    fun provideDisposer(logger: Logger): Disposer = Disposer(logger)
}
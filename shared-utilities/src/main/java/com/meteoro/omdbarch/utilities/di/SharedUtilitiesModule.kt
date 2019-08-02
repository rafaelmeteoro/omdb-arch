package com.meteoro.omdbarch.utilities.di

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import dagger.Module
import dagger.Provides

@Module
class SharedUtilitiesModule {

    @Provides
    fun provideDisposer(instance: Logger): Disposer {
        return Disposer(
            logger = instance
        )
    }
}
package com.meteoro.omdbarch.utilities.di

import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.utilities.Disposer
import dagger.Module
import dagger.Provides

@Module
class SharedUtilitiesModule(private val instance: Logger) {

    @Provides
    fun provideDisposer(): Disposer {
        return Disposer(
            logger = instance
        )
    }
}
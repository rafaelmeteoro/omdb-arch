package com.meteoro.omdbarch.components.di

import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.logger.Logger
import dagger.Module
import dagger.Provides

@Module
class ComponentsModule {

    @Provides
    fun provideDisposer(logger: Logger): Disposer = Disposer(logger)
}
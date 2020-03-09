package com.meteoro.omdbarch.domain.di

import com.meteoro.omdbarch.domain.disposer.Disposer
import com.meteoro.omdbarch.logger.Logger
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun providerDisposer(logger: Logger): Disposer = Disposer(logger)
}
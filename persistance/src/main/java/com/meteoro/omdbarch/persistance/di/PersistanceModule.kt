package com.meteoro.omdbarch.persistance.di

import android.app.Application
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import com.meteoro.omdbarch.persistance.AppPreferencesWrapper
import com.meteoro.omdbarch.persistance.SearchHistoryInfrastructure
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistanceModule {

    @Provides
    @Singleton
    fun provideSearchHistoryService(app: Application): SearchHistoryService {
        val wrapper = AppPreferencesWrapper(app)
        return SearchHistoryInfrastructure(wrapper.preferences)
    }
}
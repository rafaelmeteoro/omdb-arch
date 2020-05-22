package com.meteoro.omdbarch.persistance.di

import android.app.Application
import androidx.room.Room
import com.meteoro.omdbarch.domain.model.TypeDatabase
import com.meteoro.omdbarch.domain.services.MovieCacheService
import com.meteoro.omdbarch.domain.services.SearchHistoryService
import com.meteoro.omdbarch.persistance.AppPreferencesWrapper
import com.meteoro.omdbarch.persistance.MovieCacheRealmInfrastructure
import com.meteoro.omdbarch.persistance.MovieCacheRoomInfrastructure
import com.meteoro.omdbarch.persistance.SearchHistoryInfrastructure
import com.meteoro.omdbarch.persistance.room.OmdbRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PersistanceModule {

    @Provides
    @Singleton
    fun provideSearchHistoryService(app: Application): SearchHistoryService {
        val wrapper = AppPreferencesWrapper(app)
        return SearchHistoryInfrastructure(wrapper.preferences)
    }

    @Provides
    @Singleton
    fun provideOmdbRoomDatabase(app: Application): OmdbRoomDatabase =
        Room.databaseBuilder(app, OmdbRoomDatabase::class.java, OmdbRoomDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    @Named(TypeDatabase.ROOM)
    fun provideMovieRoomCacheService(database: OmdbRoomDatabase): MovieCacheService =
        MovieCacheRoomInfrastructure(database.movieDao())

    @Provides
    @Singleton
    @Named(TypeDatabase.REALM)
    fun provideMovieRealmCacheService(): MovieCacheService =
        MovieCacheRealmInfrastructure()
}

package com.meteoro.omdbarch.di

import android.app.Application
import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.meteoro.omdbarch.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton
import okhttp3.Interceptor

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @ElementsIntoSet
    fun provideStethoInterceptor(): Set<Interceptor> =
        if (BuildConfig.DEBUG) setOf(StethoInterceptor()) else emptySet()
}

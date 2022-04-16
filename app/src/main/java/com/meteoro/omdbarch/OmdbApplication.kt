package com.meteoro.omdbarch

import android.app.Application
import com.facebook.stetho.Stetho
import com.meteoro.omdbarch.di.DaggerAppComponent
import com.meteoro.omdbarch.logger.CrashlyticsTree
import com.meteoro.omdbarch.rest.di.RestModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class OmdbApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = injector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .restModule(RestModule(BuildConfig.API_URL, BuildConfig.API_KEY_VALUE, BuildConfig.DEBUG))
            .build()
            .inject(this)

        initStetho()
        initTimber()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }
}

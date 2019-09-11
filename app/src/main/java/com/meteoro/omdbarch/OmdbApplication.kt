package com.meteoro.omdbarch

import android.app.Application
import com.facebook.stetho.Stetho
import com.meteoro.omdbarch.di.DaggerAppComponent
import com.meteoro.omdbarch.networking.di.NetModule
import com.meteoro.omdbarch.rest.di.RestModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.serialization.UnstableDefault
import javax.inject.Inject

@UnstableDefault
class OmdbApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = injector

    override fun onCreate() {
        super.onCreate()
        initStetho()
        DaggerAppComponent.builder()
            .application(this)
            .netModule(NetModule(BuildConfig.API_KEY_VALUE, BuildConfig.DEBUG))
            .restModule(RestModule(BuildConfig.API_URL))
            .build()
            .inject(this)
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}
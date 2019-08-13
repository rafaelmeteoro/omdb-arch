package com.meteoro.omdbarch

import android.app.Application
import com.meteoro.omdbarch.di.AppModule
import com.meteoro.omdbarch.di.DaggerAppComponent
import com.meteoro.omdbarch.networking.di.NetModule
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
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(BuildConfig.API_KEY, BuildConfig.API_KEY_VALUE))
            .build()
            .inject(this)
    }
}
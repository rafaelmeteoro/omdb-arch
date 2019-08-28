package com.meteoro.omdbarch.di

import android.app.Application
import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.features.details.di.DetailsModule
import com.meteoro.omdbarch.features.home.di.HomeModule
import com.meteoro.omdbarch.networking.di.NetModule
import com.meteoro.omdbarch.rest.di.RestModule
import com.meteoro.omdbarch.utilities.di.ShareUtilsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import kotlinx.serialization.UnstableDefault
import javax.inject.Singleton

@UnstableDefault
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ShareUtilsModule::class,
        NetModule::class,
        RestModule::class,
        HomeModule::class,
        DetailsModule::class
    ]
)
interface AppComponent : AndroidInjector<OmdbApplication> {

    override fun inject(instance: OmdbApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun netModule(module: NetModule): Builder

        fun restModule(module: RestModule): Builder

        fun build(): AppComponent
    }
}

package com.meteoro.omdbarch.di

import android.app.Application
import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.details.di.DetailsModule
import com.meteoro.omdbarch.favorites.di.FavoritesModule
import com.meteoro.omdbarch.home.di.HomeModule
import com.meteoro.omdbarch.networking.di.NetModule
import com.meteoro.omdbarch.onboarding.di.OnboardingModule
import com.meteoro.omdbarch.persistance.di.PersistanceModule
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
        PersistanceModule::class,
        OnboardingModule::class,
        HomeModule::class,
        DetailsModule::class,
        FavoritesModule::class
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
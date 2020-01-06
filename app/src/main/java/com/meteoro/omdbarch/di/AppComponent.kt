package com.meteoro.omdbarch.di

import android.app.Application
import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.components.di.ComponentsModule
import com.meteoro.omdbarch.details.di.DetailsModule
import com.meteoro.omdbarch.favorites.di.FavoritesModule
import com.meteoro.omdbarch.home.di.HomeModule
import com.meteoro.omdbarch.onboarding.di.OnboardingModule
import com.meteoro.omdbarch.persistance.di.PersistanceModule
import com.meteoro.omdbarch.rest.di.RestModule
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
        ComponentsModule::class,
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

        fun restModule(module: RestModule): Builder

        fun build(): AppComponent
    }
}

package com.meteoro.omdbarch.onboarding.di

import com.meteoro.omdbarch.onboarding.OnboardingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingModule {

    @ContributesAndroidInjector
    abstract fun contributeOnboardingActivity(): OnboardingActivity
}

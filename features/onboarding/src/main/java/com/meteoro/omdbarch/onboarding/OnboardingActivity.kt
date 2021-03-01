package com.meteoro.omdbarch.onboarding

import android.view.LayoutInflater
import com.meteoro.omdbarch.actions.Actions
import com.meteoro.omdbarch.components.binding.BindingActivity
import com.meteoro.omdbarch.onboarding.databinding.ActivityOnboardingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class OnboardingActivity : BindingActivity<ActivityOnboardingBinding>() {

    companion object {
        private const val TIMER_COUNT = 1000L
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityOnboardingBinding {
        return ActivityOnboardingBinding.inflate(inflater)
    }

    override fun init() {
        Timber.d("Launch")
        handleLaunch()
    }

    private fun handleLaunch() {
        GlobalScope.launch {
            delay(TIMER_COUNT)
            goToHome()
        }
    }

    private fun goToHome() {
        startActivity(Actions.openHomeIntent(this))
        finish()
    }
}

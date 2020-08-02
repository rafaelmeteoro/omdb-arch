package com.meteoro.omdbarch.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meteoro.omdbarch.actions.Actions
import com.meteoro.omdbarch.onboarding.databinding.ActivityOnboardingBinding
import dagger.android.AndroidInjection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class OnboardingActivity : AppCompatActivity() {

    companion object {
        private const val TIMER_COUNT = 1000L
    }

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

package com.meteoro.omdbarch.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meteoro.omdbarch.dsl.Visibility
import com.meteoro.omdbarch.util.ActivityScenarioLauncher.Companion.scenarioLauncher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Test
    fun shouldDisplayEmpty() {
        scenarioLauncher<HomeActivity>().run {
            beforeLaunch { }
            onResume {
                homeListChecks {
                    loadingIndicator shouldBe Visibility.HIDDEN
                    errorLabel shouldBe Visibility.DISPLAYED
                    emptyState shouldBe Visibility.DISPLAYED
                }
            }
        }
    }
}
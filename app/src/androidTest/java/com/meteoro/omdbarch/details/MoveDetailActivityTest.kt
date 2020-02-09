package com.meteoro.omdbarch.details

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.meteoro.omdbarch.OmdbApplication
import com.meteoro.omdbarch.actions.EXTRA_IMDB
import com.meteoro.omdbarch.actions.ImdbArgs
import com.meteoro.omdbarch.components.Disposer
import com.meteoro.omdbarch.components.ViewState
import com.meteoro.omdbarch.dsl.Visibility
import com.meteoro.omdbarch.logger.Logger
import com.meteoro.omdbarch.util.ActivityScenarioLauncher.Companion.scenarioLauncher
import com.meteoro.omdbarch.util.createFakeMainActivityInjector
import io.reactivex.Flowable
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MoveDetailActivityTest {

    val mockViewModel = Mockito.mock(DetailViewModelContract::class.java)
    val mockLogger = Mockito.mock(Logger::class.java)

    @get:Rule
    val activityTestRule =
        object : ActivityTestRule<MovieDetailActivity>(MovieDetailActivity::class.java, true, false) {
            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                val myApp =
                    InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as OmdbApplication
                myApp.injector = createFakeMainActivityInjector<MovieDetailActivity> {
                    viewModel = mockViewModel
                    disposer = Disposer(mockLogger)
                }
            }

            override fun getActivityIntent(): Intent {
                val imdbArgs = ImdbArgs("")
                return Intent(InstrumentationRegistry.getInstrumentation().context, MovieDetailActivity::class.java)
                    .apply {
                        putExtra(EXTRA_IMDB, imdbArgs)
                    }
            }
        }

    fun beforeEachTest() {
        MockitoAnnotations.initMocks(this)
    }

    fun givenId_shouldDisplayMovie() {
        val presentation = MovieDetailPresentation(
            title = "Captain Marvel",
            year = "2018",
            rating = "2.6",
            cast = "Woman",
            directors = "Woman",
            plot = "Marvel",
            poster = "https://"
        )

        scenarioLauncher<MovieDetailActivity>().run {
            beforeLaunch {
                Mockito.`when`(mockViewModel.fetchMovie(Mockito.anyString()))
                    .thenReturn(Flowable.just(ViewState.Success(presentation)))
            }
            onResume {
                movieDetailListChecks {
                    loadingIndicator shouldBe Visibility.DISPLAYED
                    title shouldBe Visibility.DISPLAYED
                }
            }
        }
    }
}
package com.meteoro.omdbarch.navigator

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigatorTests {

    private lateinit var navigator: Navigator

    private val mockActivity = mock<FragmentActivity>()

    private val links = mapOf<Screen, Class<Activity>>(
        Screen.DetailMovie to Activity::class.java
    )

    @Before
    fun `before each test`() {
        navigator = Navigator(mockActivity, links)
    }

    @Test
    fun `should navigate to supported screen`() {
        navigator.navigateTo(Screen.DetailMovie)
        argumentCaptor<Intent>().apply {
            verify(mockActivity).startActivity(capture())
            assertThat(firstValue).isNotNull()
        }
    }

    @Test
    fun `should throw when navigating to unsupported screen`() {
        assertThatThrownBy { navigator.navigateTo(Screen.HomeMovies) }
            .isEqualTo(
                UnsupportedNavigation(Screen.HomeMovies)
            )
    }

    @Test
    fun `should delegate work to supported screen`() {
        navigator.requestWork(Screen.DetailMovie, DefineDetail)
        argumentCaptor<Int>().apply {
            verify(mockActivity).startActivityForResult(any(), capture())
            assertThat(firstValue).isEqualTo(DefineDetail.tag)
        }
    }

    @Test
    fun `should return from work with success`() {
        navigator.returnFromWork()
        argumentCaptor<Int>().apply {
            verify(mockActivity).setResult(capture())
            assertThat(firstValue).isEqualTo(Activity.RESULT_OK)
        }
    }
}
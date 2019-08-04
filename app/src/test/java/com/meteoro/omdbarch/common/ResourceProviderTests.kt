package com.meteoro.omdbarch.common

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

class ResourceProviderTests {

    lateinit var provider: ResourceProvider

    private val mockContext = mock<Context>()

    private val resultProvider = "result test"
    private val resIdTest = 0
    private val formatArgsTest = "args test"

    @Before
    fun `before each test`() {
        provider = ResourceProvider(
            context = mockContext
        )
    }

    @Test
    fun `should return string from any res id`() {
        whenever(mockContext.getString(anyInt()))
            .thenReturn(resultProvider)

        assertThat(provider.getString(resIdTest)).isEqualTo(resultProvider)
    }

    @Test
    fun `should return string from any res id and format args`() {
        whenever(mockContext.getString(anyInt(), anyString()))
            .thenReturn(resultProvider)

        assertThat(provider.getString(resIdTest, formatArgsTest)).isEqualTo(resultProvider)
    }
}
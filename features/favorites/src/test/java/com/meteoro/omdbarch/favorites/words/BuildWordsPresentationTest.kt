package com.meteoro.omdbarch.favorites.words

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BuildWordsPresentationTest {

    @Test
    fun `should build words presentation`() {
        val provided = listOf("matrix", "avengers", "marvel")

        val expected = WordsPresentation(
            words = listOf("matrix", "avengers", "marvel")
        )

        assertThat(BuildWordsPresentation(provided)).isEqualTo(expected)
    }
}
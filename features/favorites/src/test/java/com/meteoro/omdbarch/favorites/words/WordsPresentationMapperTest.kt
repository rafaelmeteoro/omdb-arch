package com.meteoro.omdbarch.favorites.words

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WordsPresentationMapperTest {

    lateinit var mapper: WordsPresentationMapper

    @Before
    fun `before each test`() {
        mapper = WordsPresentationMapper()
    }

    @Test
    fun `should build words presentation`() {
        val provided = listOf("matrix", "avengers", "marvel")

        val expected = WordsPresentation(
            words =  listOf("matrix", "avengers", "marvel")
        )

        assertThat(mapper.fromDomain(provided)).isEqualTo(expected)
    }
}
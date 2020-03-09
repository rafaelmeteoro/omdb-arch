package com.meteoro.omdbarch.favorites.words

class WordsPresentationMapper {

    fun fromDomain(result: List<String>): WordsPresentation =
        WordsPresentation(
            words = result
        )
}
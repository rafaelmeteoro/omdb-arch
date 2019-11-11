package com.meteoro.omdbarch.favorites.words

object BuildWordsPresentation {

    operator fun invoke(result: List<String>) =
        WordsPresentation(
            words = result
        )
}
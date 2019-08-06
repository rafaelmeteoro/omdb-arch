package com.meteoro.omdbarch.navigator

data class UnsupportedNavigation(val destination: Screen) : RuntimeException(
    "Cannot navigate to this destination -> $destination"
)
package com.meteoro.omdbarch.components

sealed class ViewState<out T> {
    object Launched : ViewState<Nothing>()
    data class Success<T>(val value: T) : ViewState<T>()
    data class Failed(val reason: Throwable) : ViewState<Nothing>()
    object Done : ViewState<Nothing>()
}
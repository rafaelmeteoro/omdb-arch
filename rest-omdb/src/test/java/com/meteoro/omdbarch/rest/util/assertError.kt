package com.meteoro.omdbarch.rest.util

fun unwrapCaughtError(result: Result<*>) =
    result.exceptionOrNull()
        ?.let { it }
        ?: throw IllegalArgumentException("Not an error")

fun assertErrorTransformed(expected: Throwable, whenRunning: () -> Any) {
    val result = whenRunning.invoke()
}
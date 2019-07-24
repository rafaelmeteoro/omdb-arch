package com.meteoro.omdbarch.domain.errors

interface ErrorTransformer {
    fun transform(incoming: Throwable): Throwable
}
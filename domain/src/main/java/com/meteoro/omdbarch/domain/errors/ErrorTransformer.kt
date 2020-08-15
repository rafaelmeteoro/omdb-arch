package com.meteoro.omdbarch.domain.errors

interface ErrorTransformer {
    suspend fun transform(incoming: Throwable): Throwable
}
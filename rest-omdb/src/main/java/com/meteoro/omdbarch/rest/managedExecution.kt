package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.networking.HttpIntegrationErrorTransformer
import com.meteoro.omdbarch.networking.NetworkingErrorTransformer
import com.meteoro.omdbarch.networking.SerializationErrorTransformer

private val transformers = listOf(
    HttpIntegrationErrorTransformer,
    NetworkingErrorTransformer,
    SerializationErrorTransformer
)

fun <T> managedExecution(target: () -> T): T =
    try {
        target.invoke()
    } catch (incoming: Throwable) {
        throw transformers
            .map { it.transform(incoming) }
            .reduce { transformed, another ->
                when {
                    transformed == another -> transformed
                    another == incoming -> transformed
                    else -> another
                }
            }
    }
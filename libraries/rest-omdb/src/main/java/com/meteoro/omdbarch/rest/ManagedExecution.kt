package com.meteoro.omdbarch.rest

import com.meteoro.omdbarch.rest.network.CorHttpIntegrationErrorTransformer
import com.meteoro.omdbarch.rest.network.CorNetworkingErrorTransformer
import com.meteoro.omdbarch.rest.network.CorSerializationErrorTransformer

private val transformers = listOf(
    CorHttpIntegrationErrorTransformer,
    CorNetworkingErrorTransformer,
    CorSerializationErrorTransformer
)

suspend fun <T> managedExecution(target: suspend () -> T): T =
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

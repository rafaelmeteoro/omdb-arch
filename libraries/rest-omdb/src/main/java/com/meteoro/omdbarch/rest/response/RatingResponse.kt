package com.meteoro.omdbarch.rest.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingResponse(
    @SerialName("Source") val source: String?,
    @SerialName("Value") val value: String?
)

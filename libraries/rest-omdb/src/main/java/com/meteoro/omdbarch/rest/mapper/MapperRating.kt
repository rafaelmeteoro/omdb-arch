package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.RatingResponse

class MapperRating {

    fun fromResponse(response: RatingResponse): Rating =
        Rating(
            source = response.source,
            value = response.value
        )
}
package com.meteoro.omdbarch.rest.mapper

import com.meteoro.omdbarch.domain.model.Rating
import com.meteoro.omdbarch.rest.response.RatingResponse

object RatingMapper {

    operator fun invoke(response: RatingResponse) = with(response) {
        Rating(
            source = source,
            value = value
        )
    }
}
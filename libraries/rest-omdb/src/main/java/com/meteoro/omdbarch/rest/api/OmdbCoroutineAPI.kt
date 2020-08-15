package com.meteoro.omdbarch.rest.api

import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbCoroutineAPI {

    @GET("/")
    suspend fun searchMovies(
        @Query("s") title: String,
        @Query("type") type: String?,
        @Query("y") year: String?
    ): SearchResponse

    @GET("/")
    suspend fun fetchMovie(
        @Query("i") id: String
    ): MovieResponse
}

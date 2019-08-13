package com.meteoro.omdbarch.rest.api

import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbAPI {
    @GET("/")
    fun searchMovies(
        @Query("s") title: String,
        @Query("type") type: String?,
        @Query("y") year: String?
    ): Observable<SearchResponse>

    @GET("/")
    fun fetchMovie(
        @Query("i") id: String
    ): Observable<MovieResponse>
}
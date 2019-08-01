package com.meteoro.omdbarch.rest.api

import com.meteoro.omdbarch.rest.response.MovieResponse
import com.meteoro.omdbarch.rest.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbAPI {
    @GET("/")
    fun getSearch(
        @Query("s") title: String,
        @Query("type") type: String?,
        @Query("y") year: String?
    ): Call<SearchResponse>

    @GET("/")
    fun getMovie(
        @Query("i") id: String
    ): Call<MovieResponse>

    companion object {
        const val API_URL = "http://www.omdbapi.com/"
        const val API_KEY = "apikey"
        const val API_KEY_VALUE = "1abc75a6"
    }
}
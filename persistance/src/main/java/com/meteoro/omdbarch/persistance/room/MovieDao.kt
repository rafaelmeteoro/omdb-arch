package com.meteoro.omdbarch.persistance.room

import androidx.room.*
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMovie: FavoriteMovieRoom)

    @Delete
    fun remove(favoriteMovie: FavoriteMovieRoom)

    @Query("DELETE FROM FAVORITE_MOVIE")
    fun clear()

    @Query("SELECT * FROM favorite_movie WHERE imdbId = :imdbId")
    fun favoriteMovie(imdbId: String): Observable<FavoriteMovieRoom>

    @Query("SELECT * FROM favorite_movie")
    fun allFavoritesMovies(): Observable<List<FavoriteMovieRoom>>
}
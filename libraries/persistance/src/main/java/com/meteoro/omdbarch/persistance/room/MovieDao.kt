package com.meteoro.omdbarch.persistance.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom
import io.reactivex.Maybe

/*
 * Maybe é usado para recuperar os filems para o stram chamar complete
 * @see https://medium.com/androiddevelopers/room-rxjava-acb0cd4f3757
 * */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteMovie: FavoriteMovieRoom)

    @Delete
    fun remove(favoriteMovie: FavoriteMovieRoom)

    @Query("DELETE FROM FAVORITE_MOVIE")
    fun clear()

    @Query("SELECT * FROM favorite_movie WHERE imdbId = :imdbId")
    fun favoriteMovie(imdbId: String): Maybe<FavoriteMovieRoom>

    @Query("SELECT * FROM favorite_movie")
    fun allFavoritesMovies(): Maybe<List<FavoriteMovieRoom>>
}

package com.meteoro.omdbarch.persistance.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meteoro.omdbarch.persistance.model.FavoriteMovieRoom

@Database(entities = [FavoriteMovieRoom::class], version = 1, exportSchema = false)
abstract class OmdbRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "omdbroom.db"
    }
}

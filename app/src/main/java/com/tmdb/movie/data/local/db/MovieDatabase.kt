package com.tmdb.movie.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tmdb.movie.data.local.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}
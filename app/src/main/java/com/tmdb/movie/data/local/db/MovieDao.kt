package com.tmdb.movie.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.movie.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("select * from movie_table")
    fun getSelectedMovies(): Flow<List<MovieEntity>>

    @Query("select movie_id from movie_table")
    suspend fun getIdsMoviesSaved(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

}

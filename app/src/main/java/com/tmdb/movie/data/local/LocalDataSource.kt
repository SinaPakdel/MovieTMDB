package com.tmdb.movie.data.local

import androidx.room.Query
import com.tmdb.movie.data.DataSource
import com.tmdb.movie.model.entity.MovieEntity
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.flow.Flow


interface LocalDataSource : DataSource {
    fun getMovies(): Flow<List<MovieItem>>

    suspend fun insertMovie(movieEntity: MovieEntity)
}
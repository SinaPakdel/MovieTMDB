package com.tmdb.movie.data.local

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.model.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


interface LocalDataSource : DataSource {
    fun getMovies(searchQuery: String): Flow<List<MovieEntity>>

    suspend fun insertMovie(movieEntity: MovieEntity)
}
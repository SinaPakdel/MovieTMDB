package com.tmdb.movie.data.local

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.data.local.model.MovieEntity
import com.tmdb.movie.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow


interface LocalDataSource : DataSource {
    fun getSelectedMovies(): Flow<List<MovieItem>>

    suspend fun getIdsMoviesSaved(): List<Int>

    suspend fun insertMovie(movieItem: MovieItem)

    suspend fun deleteMovie(movieItem: MovieItem)
}
package com.tmdb.movie.data.local

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.flow.Flow


interface LocalDataSource : DataSource {
    fun getSelectedMovies(): Flow<List<MovieItem>>

    suspend fun insertMovie(movieItem: MovieItem)
}
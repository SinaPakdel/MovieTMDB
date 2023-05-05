package com.tmdb.movie.data.remote

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem

interface RemoteDataSource : DataSource {
    suspend fun getPopularMovies(page: Int): List<MovieItem>

    suspend fun getUpcomingMovies(page: Int): List<MovieItem>


    suspend fun getMovieDetails(movieId: Int): MovieDetailsItem
}
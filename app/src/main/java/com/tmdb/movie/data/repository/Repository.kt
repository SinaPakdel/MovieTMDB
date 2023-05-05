package com.tmdb.movie.data.repository

import com.tmdb.movie.data.local.LocalDataSource
import com.tmdb.movie.data.remote.RemoteDataSource
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.flow.Flow

class Repository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getPopularMovies(page: Int) = remoteDataSource.getPopularMovies(page)

    suspend fun getUpcomingMovies(page: Int) = remoteDataSource.getUpcomingMovies(page)

    suspend fun getMovieDetails(movieId: Int) = remoteDataSource.getMovieDetails(movieId)

    fun getSeSelectedMovies(): Flow<List<MovieItem>> = localDataSource.getSelectedMovies()

    suspend fun insertMovie(movie: MovieItem) {
        localDataSource.insertMovie(movie)
    }
}
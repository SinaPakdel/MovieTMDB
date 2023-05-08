package com.tmdb.movie.data.repository

import android.util.Log
import com.tmdb.movie.data.local.LocalDataSource
import com.tmdb.movie.data.remote.RemoteDataSource
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
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

    suspend fun deleteMovie(movie: MovieItem) {
        localDataSource.deleteMovie(movie)
    }
}
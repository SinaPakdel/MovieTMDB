package com.tmdb.movie.data.repository

import android.util.Log
import com.tmdb.movie.data.local.LocalDataSource
import com.tmdb.movie.data.remote.RemoteDataSource
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.safe_api.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    fun getPopularMovies(page: Int): Flow<ResponseState<List<MovieItem>>> = remoteDataSource.getPopularMovies(page)

    fun getUpcomingMovies(page: Int): Flow<ResponseState<List<MovieItem>>> = remoteDataSource.getUpcomingMovies(page)

    fun getMovieDetails(movieId: Int): Flow<ResponseState<MovieDetailsItem>> = remoteDataSource.getMovieDetails(movieId)

    fun getSeSelectedMovies(): Flow<List<MovieItem>> = localDataSource.getSelectedMovies()

    suspend fun insertMovie(movie: MovieItem) {
        localDataSource.insertMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieItem) {
        localDataSource.deleteMovie(movie)
    }
}
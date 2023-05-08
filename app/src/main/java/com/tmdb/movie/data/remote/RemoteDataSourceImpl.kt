package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.util.safe_api.ResponseState
import com.tmdb.movie.util.safe_api.safeApiCall
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource {
    override suspend fun getPopularMovies(page: Int): Flow<ResponseState> {
        return safeApiCall { movieService.getPopularMovies(page) }
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<ResponseState> {
        return safeApiCall { movieService.getUpcomingMovies(page) }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<ResponseState> {
        return safeApiCall { movieService.getMovieDetails(movieId) }
    }
}
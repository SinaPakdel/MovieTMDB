package com.tmdb.movie.data.remote

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.safe_api.ResponseState
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource : DataSource{
    suspend fun getPopularMovies(page: Int): Flow<ResponseState>
    suspend fun getUpcomingMovies(page: Int): Flow<ResponseState>
    suspend fun getMovieDetails(movieId: Int): Flow<ResponseState>
}
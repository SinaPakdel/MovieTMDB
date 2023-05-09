package com.tmdb.movie.data.remote

import com.tmdb.movie.data.DataSource
import com.tmdb.movie.ui.model.MovieDetailsItem
import com.tmdb.movie.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource : DataSource{
     fun getPopularMovies(page: Int):  Flow<List<MovieItem>>
     fun getUpcomingMovies(page: Int): Flow<List<MovieItem>>
     fun getMovieDetails(movieId: Int): Flow<MovieDetailsItem>
}
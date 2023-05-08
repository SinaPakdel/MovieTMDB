package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.model.dto.movies.MovieResponse
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.mapper.asMovieDetailsItem
import com.tmdb.movie.util.mapper.asMoviesItem
import com.tmdb.movie.util.safe_api.ResponseState
import com.tmdb.movie.util.safe_api.safeApiCallWithMapper
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource {
    override fun getPopularMovies(page: Int): Flow<ResponseState<List<MovieItem>>> {
        return safeApiCallWithMapper({ movieService.getPopularMovies(page)
        }) {
            results.asMoviesItem()
        }
    }

    override fun getUpcomingMovies(page: Int): Flow<ResponseState<List<MovieItem>>> {
        return safeApiCallWithMapper({ movieService.getUpcomingMovies(page) }) {
            results.asMoviesItem()
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<ResponseState<MovieDetailsItem>> {
        return safeApiCallWithMapper({ movieService.getMovieDetails(movieId) }) {
            asMovieDetailsItem()
        }
    }
}
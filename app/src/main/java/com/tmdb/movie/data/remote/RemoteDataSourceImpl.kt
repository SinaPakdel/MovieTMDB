package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.model.dto.movies.MovieResponse
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.mapper.asMovieDetailsItem
import com.tmdb.movie.util.mapper.asMoviesItem
import com.tmdb.movie.util.safe_api.ResponseState
import com.tmdb.movie.util.safe_api.safeApiCall
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource {
    override  fun getPopularMovies(page: Int): Flow<ResponseState<List<MovieItem>>> {
        return safeApiCall({ movieService.getPopularMovies(page) }) {
            this.results.asMoviesItem()
        } as Flow<ResponseState<List<MovieItem>>>
    }

    override  fun getUpcomingMovies(page: Int): Flow<ResponseState<List<MovieItem>>> {
        return safeApiCall({ movieService.getUpcomingMovies(page) }) {
            this.results.asMoviesItem()
        } as Flow<ResponseState<List<MovieItem>>>
    }

    override  fun getMovieDetails(movieId: Int): Flow<ResponseState<MovieDetailsItem>> {
        return safeApiCall({ movieService.getMovieDetails(movieId) }) {
            this.asMovieDetailsItem()
        } as Flow<ResponseState<MovieDetailsItem>>
    }
}
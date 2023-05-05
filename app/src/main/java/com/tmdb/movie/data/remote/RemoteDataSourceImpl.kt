package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.mapper.asMovieDetailsItem
import com.tmdb.movie.util.mapper.asMoviesItem

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource {
    suspend fun getPopularMovies(page: Int): List<MovieItem> {
        val moviesDto = movieService.getPopularMovies(page).results
        return moviesDto.asMoviesItem()
    }

    suspend fun getUpcomingMovies(page: Int): List<MovieItem> {
        val moviesDto = movieService.getUpcomingMovies(page).results
        return moviesDto.asMoviesItem()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsItem {
        val movieDetail = movieService.getMovieDetails(movieId)
        return movieDetail.asMovieDetailsItem()
    }
}
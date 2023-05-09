package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.ui.model.MovieDetailsItem
import com.tmdb.movie.ui.model.MovieItem
import com.tmdb.movie.util.mapper.asMovieDetailsItem
import com.tmdb.movie.util.mapper.asMoviesItem
import com.tmdb.movie.util.safe_api.safeApiCallWithMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource {
    override fun getPopularMovies(page: Int): Flow<List<MovieItem>> =
        safeApiCallWithMapper { movieService.getPopularMovies(page) }.map { it.results!!.asMoviesItem() }


    override fun getUpcomingMovies(page: Int): Flow<List<MovieItem>> =
        safeApiCallWithMapper { movieService.getUpcomingMovies(page) }.map { it.results.asMoviesItem() }


    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsItem> =
        safeApiCallWithMapper { movieService.getMovieDetails(movieId) }.map { it.asMovieDetailsItem() }

}
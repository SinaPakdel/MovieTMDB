package com.tmdb.movie.data.remote.service

import com.tmdb.movie.model.dto.details.MovieDetailsResponse
import com.tmdb.movie.model.dto.movies.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieService>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int):  Response<MovieService>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailsResponse>
}
package com.tmdb.movie.data.remote

import com.tmdb.movie.model.dto.MovieDetailsResponse
import com.tmdb.movie.model.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {
    @GET("movie/{filter}")
    fun getMovies(@Query("page") page: Int, @Path("filter") filter: String): MovieResponse

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailsResponse
}
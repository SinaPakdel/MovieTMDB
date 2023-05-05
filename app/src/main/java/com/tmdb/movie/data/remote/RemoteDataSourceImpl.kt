package com.tmdb.movie.data.remote

import com.tmdb.movie.data.remote.service.MovieService

class RemoteDataSourceImpl(private val movieService: MovieService) : RemoteDataSource{
}
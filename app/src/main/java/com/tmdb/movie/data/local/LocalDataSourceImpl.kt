package com.tmdb.movie.data.local

import com.tmdb.movie.data.local.db.MovieDao
import com.tmdb.movie.model.entity.MovieEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : LocalDataSource {
    override fun getMovies(searchQuery: String) = movieDao.getMovies(searchQuery)
    override suspend fun insertMovie(movieEntity: MovieEntity) = movieDao.insertMovie(movieEntity)
}
package com.tmdb.movie.data.local

import com.tmdb.movie.data.local.db.MovieDao
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.util.mapper.asMovieEntity
import com.tmdb.movie.util.mapper.asMoviesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceImpl (private val movieDao: MovieDao) : LocalDataSource {
    override fun getSelectedMovies(): Flow<List<MovieItem>> = flow {
        movieDao.getSelectedMovies().collect { moviesEntity ->
            moviesEntity.asMoviesItem()
        }
    }

    override suspend fun insertMovie(movieItem: MovieItem) {
        val movieEntity = movieItem.asMovieEntity()
        movieDao.insertMovie(movieEntity)
    }
}


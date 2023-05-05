package com.tmdb.movie.data.repository

import com.tmdb.movie.data.local.LocalDataSource
import com.tmdb.movie.data.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {


}
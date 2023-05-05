package com.tmdb.movie.di.modules

import android.app.Application
import androidx.room.Room
import com.tmdb.movie.data.local.LocalDataSource
import com.tmdb.movie.data.local.LocalDataSourceImpl
import com.tmdb.movie.data.local.db.MovieDao
import com.tmdb.movie.data.local.db.MovieDatabase
import com.tmdb.movie.di.qualifier.DatabaseName
import com.tmdb.movie.util.consts.DataBaseName.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = DATABASE_NAME

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        @DatabaseName dataBase: String
    ): MovieDatabase =
        Room.databaseBuilder(application, MovieDatabase::class.java, dataBase)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()


    @Provides
    @Singleton
    fun provideLocalDataSource(movieDao: MovieDao): LocalDataSource = LocalDataSourceImpl(movieDao)
}
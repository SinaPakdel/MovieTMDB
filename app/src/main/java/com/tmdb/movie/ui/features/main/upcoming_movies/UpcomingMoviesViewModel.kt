package com.tmdb.movie.ui.features.main.upcoming_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpcomingMoviesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {


    private val upcomingMoviesList = arrayListOf<MovieItem>()
    private val _upcomingMovies = MutableLiveData<List<MovieItem>>()
    val upcomingMovies: LiveData<List<MovieItem>> = _upcomingMovies
    private var page = 1
    private var job: Job? = null

    fun saveMovie(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movieItem)
        }
    }

    fun getUpcomingMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            addPopularMovies(repository.getUpcomingMovies(page))
            _upcomingMovies.postValue(upcomingMoviesList)
        }
    }

    fun nextPage() {
        page++
        getUpcomingMovies()
    }

    private fun addPopularMovies(popularMovies: List<MovieItem>) {
        upcomingMoviesList.addAll(popularMovies)
    }
}
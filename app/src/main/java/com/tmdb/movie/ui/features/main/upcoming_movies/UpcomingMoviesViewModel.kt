package com.tmdb.movie.ui.features.main.upcoming_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent
import com.tmdb.movie.ui.features.main.upcoming_movies.events.UpcomingEventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _upcomingEventHandler = Channel<UpcomingEventHandler>()
    val upcomingEventHandler: Flow<UpcomingEventHandler> = _upcomingEventHandler.receiveAsFlow()

    private val upcomingMoviesList = arrayListOf<MovieItem>()
    private val _upcomingMovies = MutableLiveData<List<MovieItem>>()
    val upcomingMovies: LiveData<List<MovieItem>> = _upcomingMovies
    private var page = 1
    private var job: Job? = null

    init {
        getUpcomingMovies()
    }

    fun saveMovie(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movieItem)
        }
    }

    private fun getUpcomingMovies() {
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

    fun navigateToDetailsScreen(movieItem: Int) = viewModelScope.launch {
        _upcomingEventHandler.send(UpcomingEventHandler.NavigateToDetailsScreen(movieItem))
    }
}
package com.tmdb.movie.ui.features.main.upcoming_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.features.main.upcoming_movies.events.UpcomingEventHandler
import com.tmdb.movie.util.enums.StateHolder
import com.tmdb.movie.util.safe_api.ResponseState
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
    private val _stateHolder = MutableLiveData<StateHolder>().apply {
        StateHolder.LOADING
    }
    val stateHolder: LiveData<StateHolder> = _stateHolder

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

    private fun saveMovie(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movieItem)
        }
    }

    private fun getUpcomingMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getUpcomingMovies(page).collect { responseState ->
                when (responseState) {
                    is ResponseState.Error -> changeStateHolder(StateHolder.ERROR)
                    is ResponseState.Loading -> changeStateHolder(StateHolder.LOADING)
                    is ResponseState.Success -> {
                        changeStateHolder(StateHolder.SUCCESS)
                        addPopularMovies(responseState.data)
                        _upcomingMovies.postValue(upcomingMoviesList)
                    }
                }

            }
            _upcomingMovies.postValue(upcomingMoviesList)
        }
    }

    private fun changeStateHolder(newState: StateHolder) {
        _stateHolder.postValue(newState)
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

    fun onLikeStateClicked(movieItem: MovieItem) = viewModelScope.launch {
        saveMovie(movieItem)
        _upcomingEventHandler.send(UpcomingEventHandler.LikeStateClicked(movieItem))
    }

    fun onLongItemClicked(movieItem: MovieItem) = viewModelScope.launch {
        _upcomingEventHandler.send(UpcomingEventHandler.LongItemClicked(movieItem))
    }
}
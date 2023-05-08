package com.tmdb.movie.ui.features.main.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.features.main.popular_movies.events.PopularEvent
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
class PopularMoviesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _stateHolder = MutableLiveData<StateHolder>().apply {
        StateHolder.LOADING
    }
     val stateHolder: LiveData<StateHolder> = _stateHolder

    private val popularMoviesList = arrayListOf<MovieItem>()
    private val _popularMovies = MutableLiveData<List<MovieItem>>()
    val popularMovies: LiveData<List<MovieItem>> = _popularMovies

    private var page = 1

    private var job: Job? = null


    private val _popularEvent = Channel<PopularEvent>()
    val popularEvent: Flow<PopularEvent> = _popularEvent.receiveAsFlow()

    init {
        getPopularMovies()
    }

    private fun saveMovie(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(movieItem)
        }
    }

    private fun getPopularMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getPopularMovies(page).collect { responseState ->
                when (responseState) {
                    is ResponseState.Error -> changeStateHolder(StateHolder.ERROR)
                    is ResponseState.Loading -> changeStateHolder(StateHolder.LOADING)
                    is ResponseState.Success -> {
                        changeStateHolder(StateHolder.SUCCESS)
                        addPopularMovies(responseState.data)
                        _popularMovies.postValue(popularMoviesList)
                    }
                }
            }
        }
    }

    private fun changeStateHolder(newState : StateHolder){
        _stateHolder.postValue(newState)
    }

    fun nextPage() {
        page++
        getPopularMovies()
    }

    private fun addPopularMovies(popularMovies: List<MovieItem>) {
        popularMoviesList.addAll(popularMovies)
    }

    fun onItemMovieSelected(id: Int) = viewModelScope.launch {
        _popularEvent.send(PopularEvent.NavigateToDetailsScreen(id))
    }

    fun onLikeStateClicked(movieItem: MovieItem) = viewModelScope.launch {
        saveMovie(movieItem)
        _popularEvent.send(PopularEvent.LikeStateClicked(movieItem))
    }

    fun onLongItemMovieSelected(movieItem: MovieItem) = viewModelScope.launch {
        _popularEvent.send(PopularEvent.LongItemMovieSelected(movieItem))
    }
}
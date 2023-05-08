package com.tmdb.movie.ui.features.main.selected_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieItem
import com.tmdb.movie.ui.features.main.selected_movies.events.SelectedEventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedMoviesViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _selectedMovies = MutableLiveData<List<MovieItem>>()
    val selectedMovies: LiveData<List<MovieItem>> = _selectedMovies
    private var job: Job? = null
    private val _selectedEventHandler = Channel<SelectedEventHandler>()
    val selectedEventHandler: Flow<SelectedEventHandler> = _selectedEventHandler.receiveAsFlow()

    init {
        getSelectedMovies()
    }

    fun deleteMovie(movieItem: MovieItem) {
        viewModelScope.launch {
            repository.deleteMovie(movieItem)
        }
    }

    private fun getSelectedMovies() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSeSelectedMovies().collect { movieItem ->
                _selectedMovies.postValue(movieItem)
            }
        }
    }

    fun onItemClicked(movieId: Int) = viewModelScope.launch {
        _selectedEventHandler.send(SelectedEventHandler.ItemClicked(movieId))
    }

    fun onLikeStateClicked(movieItem: MovieItem) = viewModelScope.launch {
        repository.insertMovie(movieItem)
        _selectedEventHandler.send(SelectedEventHandler.LikeStateClicked(movieItem))
    }

    fun onLongClicked(movieItem: MovieItem) = viewModelScope.launch {
        _selectedEventHandler.send(SelectedEventHandler.LongClicked(movieItem))
    }

    fun onUndoAddedToSelectedClicked(movieItem: MovieItem) =viewModelScope.launch {
        _selectedEventHandler.send(SelectedEventHandler.UndoAddedToSelectedClicked(movieItem))
    }
}
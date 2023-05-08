package com.tmdb.movie.ui.features.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.movie.data.repository.Repository
import com.tmdb.movie.model.ui.MovieDetailsItem
import com.tmdb.movie.util.enums.StateHolder
import com.tmdb.movie.util.safe_api.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateHolder = MutableLiveData<StateHolder>().apply {
        StateHolder.LOADING
    }
    val stateHolder: LiveData<StateHolder> = _stateHolder

    private val movieId = stateHandle.get<Int>("movieId")

    private val _movie = MutableLiveData<MovieDetailsItem>()
    val movie: LiveData<MovieDetailsItem> = _movie

    init {
        movieId?.let { getMovieDetails(it) }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetails(movieId).collect { responseState ->
                when (responseState) {
                    is ResponseState.Error -> changeStateHolder(StateHolder.ERROR)
                    is ResponseState.Loading -> changeStateHolder(StateHolder.LOADING)
                    is ResponseState.Success -> {
                        changeStateHolder(StateHolder.SUCCESS)
                        _movie.postValue(responseState.data)
                    }
                }
            }
        }
    }

    private fun changeStateHolder(newState: StateHolder) {
        _stateHolder.postValue(newState)
    }

}
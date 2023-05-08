package com.tmdb.movie.util.safe_api

sealed class ResponseState {
    object Loading :ResponseState()
    data class Error(val error: ErrorState) : ResponseState()
    data class Success <T>(val data : T) : ResponseState()
}
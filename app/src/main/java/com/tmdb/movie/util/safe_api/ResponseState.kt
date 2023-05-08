package com.tmdb.movie.util.safe_api

sealed class ResponseState<out T> {
    object Loading :ResponseState<Nothing>()
    data class Error(val error: ErrorState) : ResponseState<Nothing>()
    data class Success <out T>(val data : T) : ResponseState<T>()
}
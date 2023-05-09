package com.tmdb.movie.util.safe_api

sealed class ResponseState<out T> {
    object Loading :ResponseState<Nothing>()
    data class Error(val error: ErrorState) : ResponseState<Nothing>()
    data class Success <out T>(val data : T) : ResponseState<T>()
}

fun <T> Flow<T>.asResponseState(): Flow<ResponseState<T>> = map { response ->
    ResponseState.Success(response) as ResponseState<T>
}.onStart { emit(ResponseState.Loading) }
    .catch { exception ->
        when (exception) {
            is UnSuccessfulResponseException -> {
                emit(ResponseState.Error(ErrorState.ErrorCode(exception.message.toString())))
            }
            else -> {
                emit(ResponseState.Error(ErrorState.ErrorMessage(exception.message.toString())))
            }
        }
    }
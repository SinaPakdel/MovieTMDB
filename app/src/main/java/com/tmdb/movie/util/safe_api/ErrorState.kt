package com.tmdb.movie.util.safe_api

sealed class ErrorState() {
    data class ErrorCode(val code: String): ErrorState()
    data class ErrorMessage(val message: String) : ErrorState()
}
package com.tmdb.movie.util.safe_api

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

suspend inline fun <T, K> safeApiCall(
    crossinline apiCall: suspend () -> Response<T>,
    crossinline mapper: T.() -> K? = { null }
) = flow {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody?.mapper() == null) {
                emit(ResponseState.Success(responseBody))
            } else {
                emit(ResponseState.Success(responseBody.mapper()))
            }
        } else {
            emit(ResponseState.Error(ErrorState.ErrorCode(response.message())))
        }
    } catch (e: Exception) {
        emit(ResponseState.Error(ErrorState.ErrorMessage(e.message.toString())))
    }
}.onStart { emit(ResponseState.Loading) }
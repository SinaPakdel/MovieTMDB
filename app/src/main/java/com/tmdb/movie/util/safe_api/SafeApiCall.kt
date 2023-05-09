package com.tmdb.movie.util.safe_api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

inline fun <T> safeApiCallWithMapper(
    crossinline apiCall: suspend () -> Response<T>,
): Flow<T> = flow {
    try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else {
            throw UnSuccessfulResponseException()
        }
    } catch (e: Exception) {
        throw CustomException()
    }
}
class UnSuccessfulResponseException: java.lang.Exception()
class CustomException: java.lang.Exception()
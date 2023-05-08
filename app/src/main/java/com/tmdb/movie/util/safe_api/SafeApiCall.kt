package com.tmdb.movie.util.safe_api

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

suspend inline fun <T> safeApiCall(
crossinline  apiCall : suspend () -> Response<T>
)= flow {
    try {
        val response = apiCall()
        if (response.isSuccessful){
            emit(ResponseState.Success(response.body()))
        }else{
            emit(ResponseState.Error(ErrorState.ErrorCode(response.message())))
        }
    }catch (e: Exception){
        emit(ResponseState.Error(ErrorState.ErrorMessage(e.message.toString())))
    }
}.onStart { emit(ResponseState.Loading) }
package com.tmdb.movie.util.safe_api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

//inline fun <T, K> safeApiCallWithMapper(
//    crossinline apiCall: suspend () -> Response<T>,
//    crossinline mapper: T.() -> K
//): Flow<ResponseState<K>> = flow {
//    emit(ResponseState.Loading)
//    try {
//        val response = apiCall()
//        if (response.isSuccessful) {
//            emit(ResponseState.Success(response.body()!!.mapper()))
//        } else {
//            emit(ResponseState.Error(ErrorState.ErrorCode(response.message())))
//        }
//    } catch (e: Exception) {
//        emit(ResponseState.Error(ErrorState.ErrorMessage(e.message.toString())))
//    }
//}


inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> Response<T>
): Flow<T> = flow {
    val response = apiCall()
    try {
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else {
            throw UnSuccessfulResponseException(response.message())
        }
    } catch (e: java.lang.Exception) {
        throw SystemException(e.message.toString())
    }
}

class UnSuccessfulResponseException(message: String) : java.lang.Exception(message)
class SystemException(message: String) : java.lang.Exception(message)




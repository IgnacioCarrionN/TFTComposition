package com.example.remote.service

import com.example.repository.datasource.remote.RemoteResponse
import retrofit2.Response

suspend inline fun <reified T> handleResponse(call: suspend () -> Response<T>): RemoteResponse<T> {
    return try {
        val response = call.invoke()
        try {
            if (response.isSuccessful) {
                RemoteResponse.Success(response.body()!!)
            } else {
                RemoteResponse.Error(response.errorBody()!!.toString())
            }
        } catch (e: Exception) {
            RemoteResponse.Error(e.message ?: "Unknown error message")
        }

    } catch (e: Exception) {
        RemoteResponse.Error(e.message ?: "Unknown error message")
    }
}

inline fun <reified T, reified S> RemoteResponse<T>.map(block: (T) -> S): RemoteResponse<S> {
    return when(this) {
        is RemoteResponse.Success -> RemoteResponse.Success(block.invoke(this.data))
        is RemoteResponse.Error -> RemoteResponse.Error(this.error)
    }
}
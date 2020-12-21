package com.example.repository.datasource.remote

sealed class RemoteResponse<out T> {

    data class Success<T>(val data: T): RemoteResponse<T>()

    data class Error(val error: String): RemoteResponse<Nothing>()

}
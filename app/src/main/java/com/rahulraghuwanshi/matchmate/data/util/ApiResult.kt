package com.rahulraghuwanshi.matchmate.data.util

sealed class ApiResult<out T> {

    data class Success<T>(
        val data: T
    ) : ApiResult<T>()

    data class Error(
        val message: String,
        val errorCode: String? = null
    ) : ApiResult<Nothing>()

    data object Loading : ApiResult<Nothing>()

    data object Idle : ApiResult<Nothing>()
}
package com.ksainthi.swifty.data.api

sealed class ApiResult <out T: Any, out U: Any> {
    data class Success<T: Any>(val data: T) : ApiResult<T, Nothing>()
    data class Error<U: Any>(val response: U) : ApiResult<Nothing, U>()
}
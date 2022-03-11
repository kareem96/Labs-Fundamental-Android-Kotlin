package com.kareem.appusergithub.data

sealed class Result<T>(var data: T?  = null, var message: String? = null) {
    class Success<T>(data: T?): Result<T>(data)
    class Error<T>(message: String?, data: T? = null): Result<T>(data, message)
    class Loading<T>(data: T? = null): Result<T>(data)
}

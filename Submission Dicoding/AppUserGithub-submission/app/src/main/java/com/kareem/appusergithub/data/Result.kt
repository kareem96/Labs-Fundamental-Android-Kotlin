package com.kareem.appusergithub.data

/*
sealed class Result<out R> private constructor() {
    data class Success<out T>(val data:T):Result<T>()
    data class Error(val error:String): Result<Nothing>()
    object Loading:Result<Nothing>()
}
*/

sealed class Result<T>(val data: T? = null, val message:String? = null) {
    class Success<T>(data:T?):Result<T>(data)
    class Error<T>(message: String?, data: T? = null): Result<T>(data, message)
    class Loading<T>(data: T? = null):Result<T>(data)
}
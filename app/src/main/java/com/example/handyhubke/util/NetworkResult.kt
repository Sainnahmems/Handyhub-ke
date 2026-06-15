package com.example.handyhubke.util

/**
 * A generic class that holds a value with its loading status.
 */
sealed class NetworkResult<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<out T>(data: T) : NetworkResult<T>(data)
    class Error<out T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<out T> : NetworkResult<T>()
}

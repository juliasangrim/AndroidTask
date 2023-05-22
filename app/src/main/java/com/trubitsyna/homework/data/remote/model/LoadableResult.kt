package com.trubitsyna.homework.data.remote.model

sealed class LoadableResult<T> {
    class Loading<T> : LoadableResult<T>()
    data class Success<T>(val data: T) : LoadableResult<T>()
    data class Error<T>(val throwable: Throwable) : LoadableResult<T>()
}


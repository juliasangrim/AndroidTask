package com.trubitsyna.homework.data.remote.model.auth

sealed class AuthResult<T> {
    class Validate<T> : AuthResult<T>()
    data class Success<T>(val data: T) : AuthResult<T>()
    data class NetworkError<T>(val throwable: Throwable) : AuthResult<T>()
    data class PasswordError<T>(val throwable: Throwable) : AuthResult<T>()
    data class Error<T>(val throwable: Throwable) : AuthResult<T>()
}
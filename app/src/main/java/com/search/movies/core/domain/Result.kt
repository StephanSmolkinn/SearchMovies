package com.search.movies.core.domain

typealias ErrorRoot = Error

sealed interface Result<out D, out E: ErrorRoot> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: ErrorRoot>(val error: E) : Result<Nothing, E>
}

inline fun <D, E: ErrorRoot, R> Result<D, E>.map(map: (D) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <D, E: ErrorRoot> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    return when(this) {
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Error -> this
    }
}

inline fun <D, E: ErrorRoot> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    return when(this) {
        is Result.Success -> this

        is Result.Error -> {
            action(error)
            this
        }
    }
}
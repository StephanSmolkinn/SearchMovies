package com.search.movies.core.domain

sealed interface DataError : Error {
    enum class NetworkDataError : DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        UNKNOWN,
        SERIALIZATION,
        SERVER
    }

    enum class LocalDataError : DataError {
        UNKNOWN
    }
}
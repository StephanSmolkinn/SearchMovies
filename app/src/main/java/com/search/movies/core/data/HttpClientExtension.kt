package com.search.movies.core.data

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> response(
    response: HttpResponse
): Result<T, DataError.NetworkDataError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success<T>(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.NetworkDataError.SERIALIZATION)
            }
        }
        404 -> Result.Error(DataError.NetworkDataError.NOT_FOUND)
        408 -> Result.Error(DataError.NetworkDataError.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.NetworkDataError.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(DataError.NetworkDataError.SERVER)

        else -> Result.Error(DataError.NetworkDataError.UNKNOWN)
    }
}

suspend inline fun <reified T> safeCall(
    request: () -> HttpResponse
): Result<T, DataError.NetworkDataError> {
    val result = try {
        request()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.NetworkDataError.REQUEST_TIMEOUT)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.NetworkDataError.UNKNOWN)
    }

    return response<T>(result)
}
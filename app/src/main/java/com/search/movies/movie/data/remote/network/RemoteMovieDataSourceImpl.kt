package com.search.movies.movie.data.remote.network

import com.search.movies.core.data.safeCall
import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.data.remote.dto.MovieDetailDto
import com.search.movies.movie.data.remote.dto.MovieResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.search.movies.BuildConfig
import com.search.movies.movie.data.remote.dto.MovieDto

private const val BASE_URL = "https://api.themoviedb.org"
private val API_KEY = BuildConfig.API_KEY

class RemoteMovieDataSourceImpl(
    val httpClient: HttpClient
) : RemoteMovieDataSource {
    override suspend fun searchMovies(query: String): Result<MovieResponseDto, DataError.NetworkDataError> {
       return safeCall {
            httpClient.get(urlString = "$BASE_URL/3/search/movie") {
                parameter("include_adult", false)
                parameter("language", "en-US")
                parameter("page", "1")
                parameter("api_key", API_KEY)
                parameter("query", query)
            }
       }
    }

    override suspend fun getDetailMovie(id: Int): Result<MovieDetailDto, DataError.NetworkDataError> {
        return safeCall {
            httpClient.get(urlString = "$BASE_URL/3/movie/$id") {
                parameter("api_key", API_KEY)
            }
        }
    }

    override suspend fun getPopularMovies(): Result<MovieResponseDto, DataError.NetworkDataError> {
        return safeCall<MovieResponseDto> {
            httpClient.get(urlString = "$BASE_URL/3/movie/popular") {
                parameter("language", "en-US")
                parameter("page", "1")
                parameter("api_key", API_KEY)
            }
        }
    }

    override suspend fun getTopMovies(): Result<MovieResponseDto, DataError.NetworkDataError> {
        return safeCall<MovieResponseDto> {
            httpClient.get(urlString = "$BASE_URL/3/movie/top_rated") {
                parameter("language", "en-US")
                parameter("page", "1")
                parameter("api_key", API_KEY)
            }
        }
    }

    override suspend fun getUpcomingMovies(): Result<MovieResponseDto, DataError.NetworkDataError> {
        return safeCall<MovieResponseDto> {
            httpClient.get(urlString = "$BASE_URL/3/movie/upcoming") {
                parameter("language", "en-US")
                parameter("page", "1")
                parameter("api_key", API_KEY)
            }
        }
    }
}
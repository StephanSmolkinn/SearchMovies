package com.search.movies.movie.data.remote.network

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.data.remote.dto.MovieDetailDto
import com.search.movies.movie.data.remote.dto.MovieResponseDto

interface RemoteMovieDataSource {
    suspend fun searchMovies(query: String): Result<MovieResponseDto, DataError.NetworkDataError>

    suspend fun getDetailMovie(id: Int): Result<MovieDetailDto, DataError.NetworkDataError>
}
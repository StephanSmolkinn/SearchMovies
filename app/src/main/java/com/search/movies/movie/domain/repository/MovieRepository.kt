package com.search.movies.movie.domain.repository

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.domain.model.Movie
import com.search.movies.movie.domain.model.MovieDetail

interface MovieRepository {
    suspend fun searchMovies(query: String): Result<List<Movie>, DataError.NetworkDataError>

    suspend fun getDetailMovie(id: Int): Result<MovieDetail, DataError.NetworkDataError>
}
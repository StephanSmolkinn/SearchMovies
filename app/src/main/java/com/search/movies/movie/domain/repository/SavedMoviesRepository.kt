package com.search.movies.movie.domain.repository

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.domain.model.Genre
import com.search.movies.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface SavedMoviesRepository {
    suspend fun insertMovie(movieDetail: MovieDetail, genres: List<Genre>)

    suspend fun deleteMovie(movieDetail: MovieDetail)

    fun getMovies(): Flow<Result<List<MovieDetail>, DataError.LocalDataError>>

    fun getMovieByGenre(genreName: String): Flow<Result<List<MovieDetail>, DataError.LocalDataError>>
}
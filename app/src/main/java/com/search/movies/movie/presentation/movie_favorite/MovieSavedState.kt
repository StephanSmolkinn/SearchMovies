package com.search.movies.movie.presentation.movie_favorite

import com.search.movies.core.domain.DataError
import com.search.movies.movie.domain.model.MovieDetail

data class MovieSavedState(
    val savedMovies: List<MovieDetail> = emptyList(),
    val isLoading: Boolean = false,
    val isError: DataError.LocalDataError? = null
)

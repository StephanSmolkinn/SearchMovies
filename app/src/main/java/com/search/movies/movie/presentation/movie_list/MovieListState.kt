package com.search.movies.movie.presentation.movie_list

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Error
import com.search.movies.movie.domain.model.Movie

data class MovieListState(
    val movies: List<Movie> = emptyList<Movie>(),
    val querySearch: String = "",
    val isLoading: Boolean = false,
    val isError: DataError? = null,
    val isConnectedInternet: Boolean = false
)

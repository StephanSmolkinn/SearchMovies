package com.search.movies.movie.presentation.movie_detail

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Error
import com.search.movies.movie.domain.model.MovieDetail

data class MovieDetailState(
    val movie: MovieDetail? = null,
    val isLoading: Boolean = false,
    val isError: DataError? = null
)

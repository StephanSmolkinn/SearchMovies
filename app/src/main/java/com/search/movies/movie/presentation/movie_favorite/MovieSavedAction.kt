package com.search.movies.movie.presentation.movie_favorite

import com.search.movies.movie.domain.model.MovieDetail

sealed interface MovieSavedAction {
    data class OnDeleteMovie(val movieDetail: MovieDetail)
}
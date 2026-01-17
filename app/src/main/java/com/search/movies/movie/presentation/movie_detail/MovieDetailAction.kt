package com.search.movies.movie.presentation.movie_detail

import com.search.movies.movie.domain.model.Genre
import com.search.movies.movie.domain.model.MovieDetail

sealed interface MovieDetailAction {
    data class OnSaveToFavoriteMovie(
        val movieDetail: MovieDetail,
        val genres: List<Genre>
    ) : MovieDetailAction
}
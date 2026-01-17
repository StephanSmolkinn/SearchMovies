package com.search.movies.movie.presentation.main

import kotlinx.serialization.Serializable

sealed interface MovieScreens {
    @Serializable
    data object MovieListScreen : MovieScreens
    @Serializable
    data object MovieSavedScreen : MovieScreens
    @Serializable
    data object MovieDetail : MovieScreens {
        const val ROUTE = "movie_detail/{movieId}"
        fun createRoute(id: Int): String = "movie_detail/$id"
    }
    @Serializable
    data object Profile : MovieScreens
}
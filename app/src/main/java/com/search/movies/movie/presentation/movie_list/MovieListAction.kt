package com.search.movies.movie.presentation.movie_list

interface MovieListAction {
    data class OnQuerySearchChange(val querySearch: String) : MovieListAction
    data object OnPopularMoviesClick : MovieListAction
    data object OnTopMoviesClick : MovieListAction
    data object OnUpcomingMoviesClick : MovieListAction
}
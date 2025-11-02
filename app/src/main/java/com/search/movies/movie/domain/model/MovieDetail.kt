package com.search.movies.movie.domain.model

data class MovieDetail(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Genre>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val homepage: String
)

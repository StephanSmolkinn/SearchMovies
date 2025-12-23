package com.search.movies.movie.data.remote.mappers

import com.search.movies.movie.data.remote.dto.GenreDto
import com.search.movies.movie.data.remote.dto.MovieDetailDto
import com.search.movies.movie.data.remote.dto.MovieDto
import com.search.movies.movie.domain.model.Genre
import com.search.movies.movie.domain.model.Movie
import com.search.movies.movie.domain.model.MovieDetail

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        video = this.video,
        adult = this.adult,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate
    )
}

fun Movie.toMovieDto(): MovieDto {
    return MovieDto(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        video = this.video,
        adult = this.adult,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate
    )
}

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genres = this.genres.map { it.toGenre() },
        video = this.video,
        adult = this.adult,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate,
        homepage = this.homepage
    )
}

fun MovieDetail.toMovieDetailDto(): MovieDetailDto {
    return MovieDetailDto(
        id = this.id,
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genres = this.genres.map { it.toGenreDto() },
        video = this.video,
        adult = this.adult,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        releaseDate = this.releaseDate,
        homepage = this.homepage
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun Genre.toGenreDto(): GenreDto {
    return GenreDto(
        id = this.id,
        name = this.name
    )
}
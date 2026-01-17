package com.search.movies.movie.domain.use_case

import com.search.movies.movie.domain.model.Genre
import com.search.movies.movie.domain.model.MovieDetail
import com.search.movies.movie.domain.repository.SavedMoviesRepository

class SaveMovieWithGenresUseCase(
    private val savedMoviesRepository: SavedMoviesRepository
) {
    suspend operator fun invoke(movieDetail: MovieDetail, genres: List<Genre>) {
        savedMoviesRepository.insertMovie(movieDetail, genres)
    }
}
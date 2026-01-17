package com.search.movies.movie.domain.use_case

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.core.domain.map
import com.search.movies.movie.domain.model.MovieDetail
import com.search.movies.movie.domain.repository.SavedMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSavedMoviesUseCase(
    private val savedMoviesRepository: SavedMoviesRepository
) {
    operator fun invoke(): Flow<Result<List<MovieDetail>, DataError.LocalDataError>> {
        return savedMoviesRepository.getMovies()
    }
}
package com.search.movies.movie.domain.use_case

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.domain.model.Movie
import com.search.movies.movie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTopMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Movie>, DataError.NetworkDataError>> {
        return flow {
            val result = movieRepository.getTopMovies()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
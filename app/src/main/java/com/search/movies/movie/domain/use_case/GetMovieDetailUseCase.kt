package com.search.movies.movie.domain.use_case

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.movie.domain.model.MovieDetail
import com.search.movies.movie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMovieDetailUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Flow<Result<MovieDetail, DataError.NetworkDataError>> {
        return flow {
            val result = movieRepository.getDetailMovie(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
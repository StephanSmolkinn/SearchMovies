package com.search.movies.movie.data.remote.repository

import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.core.domain.map
import com.search.movies.movie.data.remote.mappers.toMovie
import com.search.movies.movie.data.remote.mappers.toMovieDetail
import com.search.movies.movie.data.remote.network.RemoteMovieDataSource
import com.search.movies.movie.domain.model.Movie
import com.search.movies.movie.domain.model.MovieDetail
import com.search.movies.movie.domain.repository.MovieRepository

class DefaultMovieRepository(
    val remoteMovieDataSource: RemoteMovieDataSource
) : MovieRepository {
    override suspend fun searchMovies(query: String): Result<List<Movie>, DataError.NetworkDataError> {
        return remoteMovieDataSource
            .searchMovies(query)
            .map { it.result.map { movieDto -> movieDto.toMovie() } }
    }

    override suspend fun getDetailMovie(id: Int): Result<MovieDetail, DataError.NetworkDataError> {
        return remoteMovieDataSource
            .getDetailMovie(id)
            .map { movieDto -> movieDto.toMovieDetail() }
    }

    override suspend fun getPopularMovies(): Result<List<Movie>, DataError.NetworkDataError> {
        return remoteMovieDataSource
            .getPopularMovies()
            .map { it.result.map { movieDto -> movieDto.toMovie() } }
    }

    override suspend fun getTopMovies(): Result<List<Movie>, DataError.NetworkDataError> {
        return remoteMovieDataSource
            .getTopMovies()
            .map { it.result.map { movieDto -> movieDto.toMovie() } }
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>, DataError.NetworkDataError> {
        return remoteMovieDataSource
            .getUpcomingMovies()
            .map { it.result.map { movieDto -> movieDto.toMovie() } }
    }
}
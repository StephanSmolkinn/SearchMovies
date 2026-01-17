package com.search.movies.movie.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.Result
import com.search.movies.core.domain.onError
import com.search.movies.core.domain.onSuccess
import com.search.movies.core.utlis.ConnectivityObserver
import com.search.movies.movie.domain.model.Movie
import com.search.movies.movie.domain.use_case.GetPopularMoviesUseCase
import com.search.movies.movie.domain.use_case.GetTopMoviesUseCase
import com.search.movies.movie.domain.use_case.GetUpcomingMoviesUseCase
import com.search.movies.movie.domain.use_case.SearchMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private var movieJob: Job? = null
    private val _state: MutableStateFlow<MovieListState> = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    init {
        observeInternet()
        observeQuery()
    }

    fun onAction(action: MovieListAction) {
        when (action) {
            is MovieListAction.OnQuerySearchChange -> {
                _state.update {
                    it.copy(
                        querySearch = action.querySearch
                    )
                }
            }

            is MovieListAction.OnPopularMoviesClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    getMovies(
                        getPopularMoviesUseCase()
                    )
                }
            }

            is MovieListAction.OnTopMoviesClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    getMovies(
                        getTopMoviesUseCase()
                    )
                }
            }

            is MovieListAction.OnUpcomingMoviesClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    getMovies(
                        getUpcomingMoviesUseCase()
                    )
                }
            }
        }
    }

    private fun observeQuery() {
        _state.update { it.copy(isLoading = true) }
        _state
            .map { it.querySearch }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { querySearch ->
                when {
                    querySearch.length > 2 -> {
                        movieJob?.cancel()
                        movieJob = searchMovies(querySearch)
                    }

                    querySearch.isEmpty() -> {
                        movieJob?.cancel()
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun searchMovies(querySearch: String): Job {
        return searchMovieUseCase(querySearch)
            .onEach { result ->
                result
                    .onSuccess { movies ->
                        _state.update {
                            it.copy(
                                movies = movies,
                                isLoading = false
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = error,
                                movies = emptyList()
                            )
                        }
                    }
            }.launchIn(viewModelScope)
    }

    private fun observeInternet() {
        connectivityObserver
            .isConnected
            .onEach { status ->
                _state.update {
                    it.copy(
                        isConnectedInternet = status,
                        isError = null
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun getMovies(getMovies: Flow<Result<List<Movie>, DataError.NetworkDataError>>) {
        movieJob?.cancel()
        _state.update { it.copy(isLoading = true) }

        movieJob = viewModelScope.launch(Dispatchers.IO) {
            getMovies
                .distinctUntilChanged()
                .debounce(500L)
                .onEach { result ->
                    result
                        .onSuccess { movies ->
                            _state.update {
                                it.copy(
                                    movies = movies,
                                    isLoading = false
                                )
                            }
                        }
                        .onError { error ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = error,
                                    movies = emptyList()
                                )
                            }
                        }
                }.launchIn(viewModelScope)
        }
    }
}
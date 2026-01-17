package com.search.movies.movie.presentation.movie_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.movies.core.domain.DataError
import com.search.movies.core.domain.map
import com.search.movies.core.domain.onError
import com.search.movies.core.domain.onSuccess
import com.search.movies.movie.domain.model.MovieDetail
import com.search.movies.movie.domain.use_case.GetSavedMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MovieSavedViewModel(
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase
) : ViewModel() {
    private val savedMovies = getSavedMoviesUseCase.invoke()
    private val _state = MutableStateFlow<MovieSavedState>(MovieSavedState())
    val state = _state.asStateFlow()
        .onStart {
            getSaveMovies()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MovieSavedState()
        )

    fun getSaveMovies() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        savedMovies
            .onEach { result ->
                result
                    .onSuccess { movies ->
                        _state.update {
                            it.copy(
                                savedMovies = movies,
                                isLoading = false
                            )
                        }
                    }
                    .onError {
                        _state.update {
                            it.copy(
                                isError = DataError.LocalDataError.UNKNOWN,
                                isLoading = false
                            )
                        }
                    }
            }.launchIn(viewModelScope)
    }
}
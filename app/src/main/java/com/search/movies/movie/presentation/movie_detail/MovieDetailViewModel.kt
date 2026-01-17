package com.search.movies.movie.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.search.movies.core.domain.onError
import com.search.movies.core.domain.onSuccess
import com.search.movies.core.ui.SnackBarEvent
import com.search.movies.core.ui.SnackBarEventController
import com.search.movies.movie.domain.use_case.GetMovieDetailUseCase
import com.search.movies.movie.domain.use_case.SaveMovieWithGenresUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val saveMovieWithGenresUseCase: SaveMovieWithGenresUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableStateFlow<MovieDetailState> = MutableStateFlow(MovieDetailState())
    val state = _state.onStart {
        movieId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getMovieById(it)
            }
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieDetailState()
    )
    private val movieJob: Job? = null
    private val movieId = savedStateHandle.get<Int>("movieId")

    fun onAction(action: MovieDetailAction) {
        when (action) {
            is MovieDetailAction.OnSaveToFavoriteMovie -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        saveMovieWithGenresUseCase(
                            movieDetail = action.movieDetail,
                            genres = action.genres
                        )

                        SnackBarEventController.sendEvent(
                            SnackBarEvent(
                                message = "You saved the movie"
                            )
                        )
                    } catch (e: Exception) {
                        SnackBarEventController.sendEvent(
                            SnackBarEvent(
                                message = "Could not save the movie"
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun getMovieById(id: Int): Job {
        _state.update { it.copy(isLoading = true) }
        return getMovieDetailUseCase(id)
            .onEach { result ->
                result
                    .onSuccess { movie ->
                        _state.update {
                            it.copy(
                                movie = movie,
                                isLoading = false
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = error,
                            )
                        }
                    }
            }.launchIn(viewModelScope)
    }
}
package com.search.movies.movie.presentation.movie_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.search.movies.R
import com.search.movies.movie.domain.model.MovieDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    movieId: Int,
    viewModel: MovieDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollableState = rememberScrollState()
    val isVisible = remember { MutableTransitionState(false) }.apply {
        targetState = true
    }

    AnimatedVisibility(
        visibleState = isVisible,
        enter = fadeIn(tween(300, 300)),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollableState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .padding(top = 38.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator()
                    Text(
                        text = "Loading",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    if (state.movie?.backdropPath != null) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${state.movie?.backdropPath}",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .shadow(
                                    elevation = 20.dp,
                                    spotColor = Color.Black,
                                )
                                .blur(15.dp)
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxSize()
                                .background(color = Color.Black)
                        )
                    }
                    if (state.movie?.posterPath != null) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${state.movie?.posterPath}",
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(350.dp)
                                .align(Alignment.BottomCenter)
                                .padding(15.dp)
                                .clip(shape = MaterialTheme.shapes.medium)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(350.dp)
                                .align(Alignment.BottomCenter)
                                .padding(32.dp)
                                .clip(shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                ) {
                    Text(
                        text = state.movie?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(state.movie?.genres ?: emptyList()) { genre ->
                        Card {
                            Text(
                                text = genre.name,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
                state.movie?.let { movie ->
                    MovieInformation(
                        modifier = Modifier.fillMaxWidth(),
                        movie = movie,
                        onSaveMovieClick = {
                            viewModel.onAction(
                                MovieDetailAction.OnSaveToFavoriteMovie(
                                    movieDetail = movie,
                                    genres = state.movie?.genres ?: emptyList()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieInformation(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    onSaveMovieClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceDim,
        shadowElevation = 10.dp,
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Vote average ${movie.voteAverage}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Vote count ${movie.voteCount}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Popularity ${movie.popularity}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Release date ${movie.releaseDate}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(
                onClick = onSaveMovieClick,
                colors = ButtonDefaults.buttonColors(),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Save movie",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
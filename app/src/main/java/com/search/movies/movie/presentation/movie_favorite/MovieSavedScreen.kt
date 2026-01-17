package com.search.movies.movie.presentation.movie_favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.search.movies.movie.presentation.main.MovieScreens
import com.search.movies.movie.presentation.movie_favorite.components.SavedMovieItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieSavedScreen(
    navController: NavHostController,
    viewModel: MovieSavedViewModel = koinViewModel<MovieSavedViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.value.savedMovies.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "You don`t have any saved films",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Save at least one film to see it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (state.value.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LinearProgressIndicator()
                Text(
                    text = "Loading",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Adaptive(140.dp)
        ) {
            if (state.value.savedMovies.isNotEmpty()) {
                items(state.value.savedMovies) { savedMovie ->
                    SavedMovieItem(
                        savedMovie = savedMovie,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(
                                    MovieScreens.MovieDetail.createRoute(savedMovie.id)
                                )
                            }
                    )
                }
            }
        }
    }
}
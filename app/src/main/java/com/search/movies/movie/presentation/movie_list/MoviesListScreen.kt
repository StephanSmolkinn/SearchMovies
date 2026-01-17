package com.search.movies.movie.presentation.movie_list

import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.search.movies.core.domain.DataError
import com.search.movies.movie.presentation.common_components.Constants
import com.search.movies.movie.presentation.common_components.MovieItem
import com.search.movies.movie.presentation.main.MovieScreens
import com.search.movies.movie.presentation.movie_list.components.FilterCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(
    navController: NavHostController,
    viewModel: MovieListViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val filterSelectedState = rememberSaveable {
        mutableStateOf<Int?>(null)
    }
    val visible = remember { MutableTransitionState(false) }
        .apply { targetState = true }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            querySearch = state.value.querySearch,
            onQueryChange = { viewModel.onAction(MovieListAction.OnQuerySearchChange(it)) },
            onSearch = { }
        )
        Spacer(Modifier.height(16.dp))
        AnimatedVisibility(
            visibleState = visible,
            enter = fadeIn(tween(300, 300)),
            exit = fadeOut()
        ) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                itemsIndexed(Constants.filterCardItems) { index, card ->
                    FilterCard(
                        text = card.name,
                        modifier = Modifier
                            .alpha(if (filterSelectedState.value == index) 0.8f else 1f)
                            .clickable {
                                when (index) {
                                    0 -> viewModel.onAction(MovieListAction.OnPopularMoviesClick)
                                    1 -> viewModel.onAction(MovieListAction.OnTopMoviesClick)
                                    2 -> viewModel.onAction(MovieListAction.OnUpcomingMoviesClick)
                                }
                                filterSelectedState.value = index
                            }
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!state.value.movies.isEmpty()) {
                itemsIndexed(state.value.movies) { index, movie ->
                    val isVisible = remember { MutableTransitionState(false) }
                        .apply { targetState = true }

                    AnimatedVisibility(
                        visibleState = isVisible,
                        enter = fadeIn(animationSpec = tween(400, delayMillis = index * 20)),
                        exit = fadeOut()
                    ) {
                        MovieItem(
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(
                                        MovieScreens.MovieDetail.createRoute(
                                            movie.id
                                        )
                                    )
                                },
                            movie = movie
                        )
                    }
                }
            } else {
                if (!state.value.isConnectedInternet) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                imageVector = Icons.Default.Info,
                                contentDescription = "info",
                                modifier = Modifier
                                    .size(32.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "It seems you don`t have internet connection",
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Check your internet connection",
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                } else {
                    item {
                        Text(
                            text = "Search interesting movies",
                            style = TextStyle(fontSize = 18.sp, color = Color.DarkGray),
                        )
                    }
                }
            }

            if (state.value.isLoading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 23.dp))
                }
            }

            if (state.value.isError != null && state.value.isConnectedInternet) {
                item {
                    Text(
                        text = "${state.value.isError}",
                        style = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSearchBar(
    querySearch: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit
) {
    val expandState = remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = querySearch,
                onQueryChange = {
                    onQueryChange(it)
                },
                onSearch = { expandState.value = false },
                expanded = expandState.value,
                onExpandedChange = { expandState.value = it },
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            )
        },
        expanded = expandState.value,
        onExpandedChange = { expandState.value = it },
        content = { }
    )
}


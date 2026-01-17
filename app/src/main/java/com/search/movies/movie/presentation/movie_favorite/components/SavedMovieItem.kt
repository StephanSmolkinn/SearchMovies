package com.search.movies.movie.presentation.movie_favorite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.search.movies.movie.domain.model.MovieDetail

@Composable
fun SavedMovieItem(
    modifier: Modifier = Modifier,
    savedMovie: MovieDetail
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${savedMovie.posterPath}",
                contentDescription = savedMovie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
            )
            Text(
                text = savedMovie.title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
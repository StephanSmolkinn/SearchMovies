package com.search.movies.movie.presentation.common_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import com.search.movies.movie.presentation.main.MovieScreens

object Constants {
    val navigationDrawerItems = listOf(
        NavigationDrawerItem(
            title = "Movies",
            icon = Icons.Default.Search,
            badgeCount = null,
            route = MovieScreens.MovieListScreen
        ),
        NavigationDrawerItem(
            title = "Saved Movies",
            icon = Icons.Default.Favorite,
            badgeCount = 12,
            route = MovieScreens.MovieSavedScreen
        ),
        NavigationDrawerItem(
            title = "Profile",
            icon = Icons.Default.AccountCircle,
            badgeCount = null,
            route = MovieScreens.Profile
        ),
    )

    val filterCardItems = listOf(
        FilterCardItem(
            name = "Popularity",
        ),
        FilterCardItem(
            name = "Top Movies",
        ),
        FilterCardItem(
            name = "Upcoming",
        )
    )
}
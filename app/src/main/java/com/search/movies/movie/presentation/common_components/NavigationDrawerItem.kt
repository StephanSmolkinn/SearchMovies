package com.search.movies.movie.presentation.common_components

import androidx.compose.ui.graphics.vector.ImageVector
import com.search.movies.movie.presentation.main.MovieScreens

data class NavigationDrawerItem(
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int? = null,
    val route: MovieScreens
)


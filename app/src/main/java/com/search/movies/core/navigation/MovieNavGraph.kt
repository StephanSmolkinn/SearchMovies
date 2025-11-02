package com.search.movies.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.search.movies.movie.presentation.main.MainScreen

fun NavGraphBuilder.movieNavGraph(navController: NavController) {
    navigation<NavGraph.MovieNavGraph>(
        startDestination = NavGraph.MovieNavGraph.MainScreen
    ) {
        composable<NavGraph.MovieNavGraph.MainScreen> {
            MainScreen(navController)
        }
    }
}
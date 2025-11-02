package com.search.movies.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.search.movies.auth.presentation.login_screen.LoginScreen
import com.search.movies.auth.presentation.login_screen.LoginViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<NavGraph.AuthNavGraph>(
        startDestination = NavGraph.AuthNavGraph.LoginScreen,
    ) {
        composable<NavGraph.AuthNavGraph.LoginScreen> {
            LoginScreen(viewModel = koinViewModel<LoginViewModel>())
        }
    }
}
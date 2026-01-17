package com.search.movies.movie.presentation.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.search.movies.core.ui.ObserveSnackBar
import com.search.movies.core.ui.SnackBarEventController
import com.search.movies.movie.presentation.profile_screen.ProfileScreen
import com.search.movies.movie.presentation.common_components.Constants.navigationDrawerItems
import com.search.movies.movie.presentation.common_components.TopBar
import com.search.movies.movie.presentation.movie_detail.MovieDetailScreen
import com.search.movies.movie.presentation.movie_favorite.MovieSavedScreen
import com.search.movies.movie.presentation.movie_list.MovieListAction
import com.search.movies.movie.presentation.movie_list.MovieListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedIndex = remember { mutableStateOf(0) }
    val navController = rememberNavController()
    val snackBarState = remember { SnackbarHostState() }

    ObserveSnackBar(flow = SnackBarEventController.channelEvents) { event ->
        scope.launch {
            snackBarState.showSnackbar(event.message)
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                navigationDrawerItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedIndex.value,
                        onClick = {
                            selectedIndex.value = index
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = it.toString())
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(
                    title = navigationDrawerItems[selectedIndex.value].title,
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            snackbarHost = {
                SnackbarHost(snackBarState)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MovieScreens.MovieListScreen,
                modifier = Modifier
                    .padding(it)
            ) {
                composable<MovieScreens.MovieListScreen> {
                    MovieListScreen(navController)
                }
                composable<MovieScreens.MovieSavedScreen> {
                    MovieSavedScreen(navController)
                }
                composable(
                    route = MovieScreens.MovieDetail.ROUTE,
                    arguments = listOf(
                        navArgument(
                            name = "movieId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    MovieDetailScreen(
                        navController = navController,
                        movieId = it.arguments?.getInt("movieId") ?: -1
                    )
                }
                composable<MovieScreens.Profile> {
                    ProfileScreen(navController)
                }
            }
        }
    }
}

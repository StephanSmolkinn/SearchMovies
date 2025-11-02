package com.search.movies.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavGraph {
    @Serializable
    data object AuthNavGraph : NavGraph {
        @Serializable
        data object LoginScreen
    }
    @Serializable
    data object MovieNavGraph : NavGraph {
        @Serializable
        data object MainScreen
    }
}
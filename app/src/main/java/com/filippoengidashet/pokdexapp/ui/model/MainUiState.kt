package com.filippoengidashet.pokdexapp.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberMainUiState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    MainUiState(
        navController = navController,
    )
}

class MainUiState(
    val navController: NavHostController,
) {

    fun onBack() = navController.popBackStack()
    fun onNavigate(route: String) = navController.navigate(route)
}

sealed class ScreenDestination(val route: String) {

    object Home : ScreenDestination("home")

    object Detail : ScreenDestination("detail/{name}") {

        fun createNavRoute(name: String) = "detail/$name"
    }

    object Favourites : ScreenDestination("favourites")

    companion object {
        const val ARGS_POKEMON_NAME = "name"
//        const val ARGS_POKEMON_IS_FAVOURITE = "isFavourite"
    }
}

package com.filippoengidashet.pokdexapp.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.filippoengidashet.pokdexapp.common.orElse
import com.filippoengidashet.pokdexapp.ui.model.MainUiState
import com.filippoengidashet.pokdexapp.ui.model.ScreenDestination
import com.filippoengidashet.pokdexapp.ui.model.rememberMainUiState
import com.filippoengidashet.pokdexapp.ui.screens.detail.PokemonDetailScreen
import com.filippoengidashet.pokdexapp.ui.screens.favourites.FavouriteListScreen
import com.filippoengidashet.pokdexapp.ui.screens.list.PokemonListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainUiState: MainUiState = rememberMainUiState()) {
    NavHost(
        navController = mainUiState.navController,
        startDestination = ScreenDestination.Home.route
    ) {
        composable(route = ScreenDestination.Home.route) { entry: NavBackStackEntry ->
            PokemonListScreen(
                onNavigateToFavouritesScreen = {
                    mainUiState.onNavigate(ScreenDestination.Favourites.route)
                },
                onNavigateToDetailScreen = { pokemon ->
                    mainUiState.onNavigate(ScreenDestination.Detail.createNavRoute(pokemon.name))
                }
            )
        }
        composable(
            route = ScreenDestination.Detail.route,
            arguments = listOf(
                navArgument(ScreenDestination.ARGS_POKEMON_NAME) { type = NavType.StringType },
            )
        ) { entry: NavBackStackEntry ->

            val name = entry.arguments?.getString(ScreenDestination.ARGS_POKEMON_NAME).orElse { "" }

            PokemonDetailScreen(
                name = name,
                onNavigateBack = {
                    mainUiState.onBack()
                },
            )
        }
        composable(route = ScreenDestination.Favourites.route) { entry: NavBackStackEntry ->
            FavouriteListScreen(
                onNavigateBack = {
                    mainUiState.onBack()
                },
                onNavigateToDetailScreen = { name ->
                    mainUiState.onNavigate(ScreenDestination.Detail.createNavRoute(name))
                },
            )
        }
    }
}

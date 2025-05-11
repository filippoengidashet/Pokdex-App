package com.filippoengidashet.pokdexapp.ui.screens.favourites

import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FavouriteListViewModel

    @Before
    fun setUp() {

        val repository = FakeFavoritesRepository()

        val getAllFavouritesPokemonUseCase = GetAllFavouritesPokemonUseCase(repository = repository)
        val removeFavouritePokemonUseCase = RemoveFavouritePokemonUseCase(repository = repository)

        viewModel = FavouriteListViewModel(
            removeFavouritePokemonUseCase = removeFavouritePokemonUseCase,
            getAllFavouritesPokemonUseCase = getAllFavouritesPokemonUseCase,
        )
    }

    @Test
    fun favouriteListScreen() {
        composeTestRule.setContent {
            FavouriteListScreen(
                onNavigateBack = {},
                onNavigateToDetailScreen = {},
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithText("Favourites").assertExists()
        composeTestRule.onNodeWithText("bulbasaur").assertExists()

        composeTestRule.onNodeWithText("bulbasaur").performScrollToNode(
            hasTestTag("delete_favourite_button")
        ).performClick()

        composeTestRule.onNodeWithText("bulbasaur")
            .onChildren()
            //.filter(hasTestTag("delete_favourite_button"))
            .filterToOne(hasTestTag("delete_favourite_button"))
            .performClick()
        //.performClick()

        composeTestRule.onNodeWithText("bulbasaur").assertDoesNotExist()

        //TODO complete test and add more usecases
    }

    @After
    fun tearDown() {

    }
}

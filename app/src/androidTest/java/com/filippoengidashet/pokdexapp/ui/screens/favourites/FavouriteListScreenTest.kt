package com.filippoengidashet.pokdexapp.ui.screens.favourites

import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import com.filippoengidashet.pokdexapp.ui.components.DELETE_FAVOURITE_BUTTON_TEST_TAG
import kotlinx.coroutines.test.runTest
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
    fun favouriteListScreen_removeItem() = runTest {
        composeTestRule.setContent {
            FavouriteListScreen(
                onNavigateBack = {},
                onNavigateToDetailScreen = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(FAVOURITES_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(FAVOURITES_TEST_TAG)
            .performScrollToNode(hasText("bulbasaur"))
            .onChildren()
            .filterToOne(hasText("bulbasaur"))
            .onChildren()
            .filterToOne(hasTestTag(DELETE_FAVOURITE_BUTTON_TEST_TAG))
            .performClick()

        composeTestRule
            .onNodeWithText("Are you sure you want to delete this item?")
            .isNotDisplayed()

        composeTestRule.onNodeWithText("Yes").performClick()

        composeTestRule.waitUntil(1200) { composeTestRule.onNodeWithText("Yes").isNotDisplayed() }

        composeTestRule.waitUntil { composeTestRule.onNodeWithText("bulbasaur").isNotDisplayed() }

        composeTestRule.onNodeWithText("bulbasaur").assertDoesNotExist()

        //TODO complete test and add more usecases
    }

    @After
    fun tearDown() {

    }
}

package com.filippoengidashet.pokdexapp.ui.screens.favourites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.filippoengidashet.pokdexapp.R
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.ui.components.AppToolbar
import com.filippoengidashet.pokdexapp.ui.components.EmptyContentUiState
import com.filippoengidashet.pokdexapp.ui.components.FavouriteListItem
import com.filippoengidashet.pokdexapp.ui.popup.DeleteConfirmationPopup
import java.util.Optional

@Composable
fun FavouriteListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetailScreen: (name: String) -> Unit,
    viewModel: FavouriteListViewModel = hiltViewModel(),
) {

    val favoriteEntities = viewModel.favourites.collectAsStateWithLifecycle().value

    var showDeleteAllConfirmationPopup by remember { mutableStateOf(false) }
    var deleteFavouriteCandidate by remember { mutableStateOf(Optional.empty<FavoriteItem>()) }

    if (showDeleteAllConfirmationPopup) {
        DeleteConfirmationPopup(
            message = stringResource(R.string.text_delete_favourites_confirmation),
            onDismiss = { confirmed ->
                if (confirmed) {
                    viewModel.deleteAllFavourites()
                }
                showDeleteAllConfirmationPopup = false
            },
        )
    }

    if (deleteFavouriteCandidate.isPresent) {
        DeleteConfirmationPopup(
            message = stringResource(R.string.text_delete_favourite_confirmation),
            onDismiss = { confirmed ->
                if (confirmed) {
                    viewModel.deleteFavorite(deleteFavouriteCandidate.get().name)
                }
                deleteFavouriteCandidate = Optional.empty()
            },
        )
    }

    Scaffold(
        topBar = {
            AppToolbar(
                icon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.favourites), color = Color.White) },
                actions = {
                    if (favoriteEntities.isNotEmpty()) {
                        IconButton(onClick = {
                            showDeleteAllConfirmationPopup = true
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete_sweep),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        if (favoriteEntities.isEmpty()) {
            EmptyContentUiState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                message = stringResource(R.string.text_no_favourite_placeholder),
            )
        } else {
            LazyColumn(
                Modifier
                    .padding(innerPadding)
                    .testTag(FAVOURITES_TEST_TAG)
            ) {
                items(favoriteEntities, key = { it.name }) { item ->
                    FavouriteListItem(
                        item = item,
                        onDelete = {
                            deleteFavouriteCandidate = Optional.of(item)
                        },
                        onNavigateToDetail = {
                            onNavigateToDetailScreen.invoke(item.name)
                        },
                    )
                }
            }
        }
    }
}

const val FAVOURITES_TEST_TAG = "favourites_test_tag"

package com.filippoengidashet.pokdexapp.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.filippoengidashet.pokdexapp.R
import com.filippoengidashet.pokdexapp.domain.model.PokemonItem
import com.filippoengidashet.pokdexapp.ui.components.AppToolbar
import com.filippoengidashet.pokdexapp.ui.components.EmptyContentUiState
import com.filippoengidashet.pokdexapp.ui.components.ErrorItemComponent
import com.filippoengidashet.pokdexapp.ui.components.PokemonListItem

@Composable
fun PokemonListScreen(
    onNavigateToFavouritesScreen: () -> Unit,
    onNavigateToDetailScreen: (name: PokemonItem) -> Unit,
    viewModel: PokeListViewModel = hiltViewModel(),
) {

    val lazyListState = rememberLazyListState()
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()
    val favourites = viewModel.favouriteListState.collectAsStateWithLifecycle().value

    val loadState = pagingItems.loadState

    val prependState = loadState.prepend
    val refreshState = loadState.refresh
    val appendState = loadState.append

    Scaffold(
        topBar = {
            AppToolbar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pokeapi_banner),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
                        contentDescription = null,
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onNavigateToFavouritesScreen.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorites),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            LazyColumn(state = lazyListState) {

                val count = pagingItems.itemCount

                if (count == 0 &&
                    appendState is LoadState.NotLoading &&
                    refreshState is LoadState.NotLoading
                ) {
                    item {
                        EmptyContentUiState()
                    }
                } else {
                    items(
                        count = count, key = pagingItems.itemKey { it.name }
                    ) { index ->

                        pagingItems[index]?.let { item ->

                            val isFavourite = favourites.any { it.name == item.name }

                            PokemonListItem(
                                position = (index + 1),
                                item = item,
                                isFavourite = isFavourite,
                                onToggleFavourite = {
                                    viewModel.toggleFavourite(item.name, it)
                                },
                                modifier = Modifier.clickable {
                                    onNavigateToDetailScreen.invoke(item)
                                }
                            ) {
                                if (index < count - 1) {
                                    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
                                }
                            }
                        }
                    }
                }

                if (refreshState is LoadState.Error) {
                    val errorMessage = refreshState.error.localizedMessage
                    item {
                        ErrorItemComponent(message = "Loading error: $errorMessage") {
                            pagingItems.retry()
                        }
                    }
                }

                if (appendState is LoadState.Error) {
                    val appendErrorMessage = appendState.error.localizedMessage
                    item {
                        ErrorItemComponent("Loading error: $appendErrorMessage") {
                            pagingItems.retry()
                        }
                    }
                }
            }

            if (prependState is LoadState.Loading
                || refreshState is LoadState.Loading
                || appendState is LoadState.Loading
            ) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

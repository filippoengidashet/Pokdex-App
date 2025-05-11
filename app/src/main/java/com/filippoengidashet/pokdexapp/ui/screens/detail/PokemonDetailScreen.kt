package com.filippoengidashet.pokdexapp.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.filippoengidashet.pokdexapp.ui.components.AppToolbar
import com.filippoengidashet.pokdexapp.ui.components.EmptyContentUiState
import com.filippoengidashet.pokdexapp.ui.components.GroupTitle
import com.filippoengidashet.pokdexapp.ui.components.TextDetail

@Composable
fun PokemonDetailScreen(
    name: String,
    onNavigateBack: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.loadPokemonDetail(name)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val favourites = viewModel.favouritesState.collectAsStateWithLifecycle().value

    val pokemonDetail = uiState.result
    val isLoading = uiState.isLoading
    val isFavourite = favourites.any { it.name == name }

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
                title = {
                    Text(
                        text = name.capitalize(Locale.current),
                        color = Color.White
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CutCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
                onClick = {
                    viewModel.toggleFavourite(name, isFavourite)
                }
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavourite) Color.Red else Color.Gray
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
        ) {

            if (uiState.error != null) {
                EmptyContentUiState(
                    modifier = Modifier.fillMaxSize(),
                    message = uiState.getErrorMessage("Unknown error")
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadPokemonDetail(name) }) {
                        Text("Retry")
                    }
                }
            } else {

                Column(Modifier.verticalScroll(scrollState)) {

                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .height(295.dp),
                        model = pokemonDetail?.officialArtwork,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Pokemon Image",
                    )

                    GroupTitle(
                        title = "Physical properties"
                    )

                    pokemonDetail?.weight?.let {
                        TextDetail(title = "Weight:", description = it)
                    }

                    pokemonDetail?.height?.let {
                        TextDetail(title = "Height:", description = it)
                    }

                    GroupTitle(
                        title = "Base Stats"
                    )

                    pokemonDetail?.stats?.let {
                        it.hpStats?.let { it1 ->
                            TextDetail(
                                title = "HP:",
                                description = it1.toString()
                            )
                        }
                        it.attackStats?.let { it1 ->
                            TextDetail(
                                title = "Attack:",
                                description = it1.toString()
                            )
                        }
                        it.defenceStats?.let { it1 ->
                            TextDetail(
                                title = "Defence:",
                                description = it1.toString()
                            )
                        }
                        it.speedStats?.let { it1 ->
                            TextDetail(
                                title = "Speed:",
                                description = it1.toString()
                            )
                        }
                    }

                    GroupTitle(
                        title = "Types"
                    )

                    pokemonDetail?.types?.forEach {
                        TextDetail(title = it.name, description = "")
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

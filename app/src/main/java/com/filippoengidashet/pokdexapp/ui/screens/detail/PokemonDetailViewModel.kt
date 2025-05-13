package com.filippoengidashet.pokdexapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.usecase.AddFavouritePokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.GetPokemonDetailUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailUseCase: GetPokemonDetailUseCase,
    private val addFavouritePokemonUseCase: AddFavouritePokemonUseCase,
    private val removeFavouritePokemonUseCase: RemoveFavouritePokemonUseCase,
    private val getAllFavouritesPokemonUseCase: GetAllFavouritesPokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState = _uiState.asStateFlow()

    val favouriteListState = getAllFavouritesPokemonUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun loadPokemonDetail(name: String) {
        if (uiState.value.isFreshLoad() || uiState.value.isFailed()) {
            _uiState.value = PokemonDetailUiState(isLoading = true)
            viewModelScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) { pokemonDetailUseCase(name) }
                    _uiState.value = uiState.value.copy(result = result, isLoading = false)
                } catch (e: Exception) {
                    _uiState.value = uiState.value.copy(
                        error = ErrorUiState(e.message),
                        isLoading = false
                    )
                }
            }
        }

    }

    fun toggleFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                removeFavouritePokemonUseCase(name)
            } else {
                val item = FavoriteItem(name = name)
                addFavouritePokemonUseCase(item)
            }
        }
    }
}

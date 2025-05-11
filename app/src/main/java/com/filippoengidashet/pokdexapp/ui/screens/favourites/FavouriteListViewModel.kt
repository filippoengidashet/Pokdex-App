package com.filippoengidashet.pokdexapp.ui.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteListViewModel @Inject constructor(
    private val removeFavouritePokemonUseCase: RemoveFavouritePokemonUseCase,
    private val getAllFavouritesPokemonUseCase: GetAllFavouritesPokemonUseCase,
) : ViewModel() {

    private val _favourites = MutableStateFlow<List<FavoriteItem>>(emptyList())
    val favourites: StateFlow<List<FavoriteItem>> = _favourites

    init {
        loadAllFavourites()
    }

    private fun loadAllFavourites() {
        viewModelScope.launch {
            getAllFavouritesPokemonUseCase().collect {
                _favourites.value = it
            }
        }
    }

    fun deleteFavorite(name: String) {
        viewModelScope.launch {
            removeFavouritePokemonUseCase(name)
            loadAllFavourites()
        }
    }

    fun deleteAllFavourites() {
        viewModelScope.launch {
            favourites.value.forEach {
                removeFavouritePokemonUseCase(it.name)
            }
            loadAllFavourites()
        }
    }
}

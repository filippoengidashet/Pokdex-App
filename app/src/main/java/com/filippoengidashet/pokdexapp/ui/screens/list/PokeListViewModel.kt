package com.filippoengidashet.pokdexapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.usecase.AddFavouritePokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.GetPokemonResultsUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val getPokeListUseCase: GetPokemonResultsUseCase,
    private val addFavouritePokemonUseCase: AddFavouritePokemonUseCase,
    private val removeFavouritePokemonUseCase: RemoveFavouritePokemonUseCase,
    getAllFavouritesPokemonUseCase: GetAllFavouritesPokemonUseCase,
) : ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = PokeResultsPagingSource.PAGE_SIZE,
            prefetchDistance = PokeResultsPagingSource.PREFETCH_DISTANCE,
        ),
        pagingSourceFactory = {
            PokeResultsPagingSource(getPokeListUseCase)
        }
    ).flow.cachedIn(viewModelScope)

    val favouritesState = getAllFavouritesPokemonUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList()
    )

    fun toggleFavourite(name: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                val item = FavoriteItem(name = name)
                addFavouritePokemonUseCase(item)
            } else {
                removeFavouritePokemonUseCase(name)
            }
        }
    }
}

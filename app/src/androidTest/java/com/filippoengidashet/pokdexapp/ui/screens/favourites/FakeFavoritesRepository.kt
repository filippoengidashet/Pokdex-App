package com.filippoengidashet.pokdexapp.ui.screens.favourites

import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFavoritesRepository : FavoritesRepository {

    private val favourites = mutableListOf<FavoriteItem>(
        FavoriteItem("bulbasaur"),
        FavoriteItem("charmander"),
        FavoriteItem("squirtle"),
    )

    override suspend fun addFavourite(item: FavoriteItem) {
        favourites.add(item)
    }

    override suspend fun removeFavourite(name: String) {
        favourites.removeIf { it.name == name }
    }

    override fun getAllFavourites(): Flow<List<FavoriteItem>> {
        return flowOf(favourites)
    }
}

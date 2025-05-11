package com.filippoengidashet.pokdexapp.domain.repository

import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun addFavourite(item: FavoriteItem)

    suspend fun removeFavourite(name: String)

    fun getAllFavourites(): Flow<List<FavoriteItem>>
}

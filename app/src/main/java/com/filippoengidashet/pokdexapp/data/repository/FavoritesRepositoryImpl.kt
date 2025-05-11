package com.filippoengidashet.pokdexapp.data.repository

import com.filippoengidashet.pokdexapp.data.database.FavoriteEntity
import com.filippoengidashet.pokdexapp.data.database.FavouritesDao
import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favouritesDao: FavouritesDao
) : FavoritesRepository {

    override suspend fun addFavourite(item: FavoriteItem) {
        val entity = FavoriteEntity(name = item.name)
        favouritesDao.insertFavourite(entity)
    }

    override suspend fun removeFavourite(name: String) {
        favouritesDao.deleteFavourite(name)
    }

    override fun getAllFavourites(): Flow<List<FavoriteItem>> {
        return favouritesDao.getAllFavourites().map { favourites ->
            favourites.map { entity ->
                FavoriteItem(name = entity.name)
            }
        }
    }
}

package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.model.FavoriteItem
import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavouritePokemonUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {

    suspend operator fun invoke(item: FavoriteItem) = repository.addFavourite(item)
}

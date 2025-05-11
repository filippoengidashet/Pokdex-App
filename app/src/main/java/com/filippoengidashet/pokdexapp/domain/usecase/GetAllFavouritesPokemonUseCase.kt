package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetAllFavouritesPokemonUseCase @Inject constructor(
    private val repository: FavoritesRepository,
) {

    operator fun invoke() = repository.getAllFavourites()
}

package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import javax.inject.Inject

class RemoveFavouritePokemonUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {

    suspend operator fun invoke(name: String) = repository.removeFavourite(name)
}

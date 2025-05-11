package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonResultsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(
        offset: Int, limit: Int
    ) = repository.getPokemonResults(offset, limit)
}
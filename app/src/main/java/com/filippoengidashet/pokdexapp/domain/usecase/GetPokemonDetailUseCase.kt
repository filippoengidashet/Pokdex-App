package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(name: String) = repository.getPokemonDetail(name)
}

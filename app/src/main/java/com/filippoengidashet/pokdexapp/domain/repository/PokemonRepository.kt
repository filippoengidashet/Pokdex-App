package com.filippoengidashet.pokdexapp.domain.repository

import com.filippoengidashet.pokdexapp.domain.model.PokemonDetail
import com.filippoengidashet.pokdexapp.domain.model.PokemonResults

interface PokemonRepository {

    suspend fun getPokemonResults(offset: Int, limit: Int): PokemonResults

    suspend fun getPokemonDetail(name: String): PokemonDetail
}

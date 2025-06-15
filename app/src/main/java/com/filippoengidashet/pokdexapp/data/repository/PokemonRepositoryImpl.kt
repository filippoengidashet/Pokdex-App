package com.filippoengidashet.pokdexapp.data.repository

import com.filippoengidashet.pokdexapp.data.api.PokemonApiService
import com.filippoengidashet.pokdexapp.data.mapper.toDomain
import com.filippoengidashet.pokdexapp.domain.model.PokemonDetail
import com.filippoengidashet.pokdexapp.domain.model.PokemonResults
import com.filippoengidashet.pokdexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonApiService,
) : PokemonRepository {

    override suspend fun getPokemonResults(offset: Int, limit: Int): PokemonResults {
        return apiService.getPokemonResults(offset = offset, limit = limit).toDomain()
    }

    override suspend fun getPokemonDetail(name: String): PokemonDetail {
        return apiService.getPokemonDetail(name = name).let {
            it.toDomain()
        }
    }
}

package com.filippoengidashet.pokdexapp.data.mapper

import com.filippoengidashet.pokdexapp.data.model.PokemonDetailDto
import com.filippoengidashet.pokdexapp.data.model.PokemonResultDto
import com.filippoengidashet.pokdexapp.data.model.PokemonResultsDto
import com.filippoengidashet.pokdexapp.data.model.PokemonStatObjDto
import com.filippoengidashet.pokdexapp.data.model.PokemonTypeObjDto
import com.filippoengidashet.pokdexapp.domain.model.PokemonDetail
import com.filippoengidashet.pokdexapp.domain.model.PokemonItem
import com.filippoengidashet.pokdexapp.domain.model.PokemonResults
import com.filippoengidashet.pokdexapp.domain.model.PokemonStats
import com.filippoengidashet.pokdexapp.domain.model.PokemonType

fun PokemonResultsDto.toDomain(): PokemonResults = PokemonResults(
    count = count,
    next = next,
    previous = previous,
    results = results.map { it.toDomain() }
)

fun PokemonResultDto.toDomain(): PokemonItem = PokemonItem(
    name = name,
    url = url,
)

fun PokemonDetailDto.toDomain(): PokemonDetail = PokemonDetail(
    id = id,
    weight = weight.toString(),
    height = height.toString(),
    officialArtwork = sprites?.other?.officialArtwork?.front_default,
    stats = mapPokemonStats(stats),
    types = mapPokemonTypes(types),
)

fun mapPokemonTypes(typesList: List<PokemonTypeObjDto>): List<PokemonType> {
    val types = mutableListOf<PokemonType>()
    for (typeObjDto in typesList) {
        types.add(
            with(typeObjDto.type) {
                PokemonType(
                    slot = typeObjDto.slot,
                    name = name,
                    url = url
                )
            }
        )
    }
    return types
}

fun mapPokemonStats(statsList: List<PokemonStatObjDto>): PokemonStats? {

    val hpStats = statsList.find { it.stat.name == "hp" }?.base_stat
    val defenceStats = statsList.find { it.stat.name == "defense" }?.base_stat
    val attackStats = statsList.find { it.stat.name == "attack" }?.base_stat
    val speedStats = statsList.find { it.stat.name == "speed" }?.base_stat

    return PokemonStats(
        hpStats = hpStats,
        defenceStats = defenceStats,
        attackStats = attackStats,
        speedStats = speedStats
    )
}

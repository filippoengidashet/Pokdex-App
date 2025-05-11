package com.filippoengidashet.pokdexapp.domain.model

data class PokemonResults(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem> = emptyList(),
)

data class PokemonItem(
    val name: String,
    val url: String,
    //val isFavourite: Boolean = false,
)

data class PokemonDetail(
    val id: String,
    val weight: String?,
    val height: String?,
    val officialArtwork: String?,
    val stats: PokemonStats?,
    val types: List<PokemonType>,
)

data class PokemonStats(
    val hpStats: Int?,
    val defenceStats: Int?,
    val attackStats: Int?,
    val speedStats: Int?,
)

data class PokemonType(
    val slot: Int?,
    val name: String,
    val url: String,
)

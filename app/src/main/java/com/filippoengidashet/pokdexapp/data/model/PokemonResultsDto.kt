package com.filippoengidashet.pokdexapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResultsDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResultDto>,
)

data class PokemonResultDto(
    val name: String,
    val url: String,
)

data class PokemonDetailDto(
    val id: String,
    val weight: Int,
    val height: Int,
    val sprites: PokemonSpriteDto?,
    val stats: List<PokemonStatObjDto>,
    val types: List<PokemonTypeObjDto>,
)

data class PokemonSpriteDto(
    val back_default: String?,
    val other: PokemonSpriteOtherDto,
)

data class PokemonSpriteOtherDto(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonSpriteOfficialArtworkDto
)

data class PokemonSpriteOfficialArtworkDto(
    val front_default: String?
)

data class PokemonStatObjDto(
    val base_stat: Int?,
    val effort: Int?,
    val stat: PokemonStatDto,
)

data class PokemonStatDto(
    val name: String,
    val url: String,
)

data class PokemonTypeObjDto(
    val slot: Int?,
    val type: PokemonTypeDto,
)

data class PokemonTypeDto(
    val name: String,
    val url: String,
)

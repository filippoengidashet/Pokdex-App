package com.filippoengidashet.pokdexapp.data.api

import com.filippoengidashet.pokdexapp.data.model.PokemonDetailDto
import com.filippoengidashet.pokdexapp.data.model.PokemonResultsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    //"https://pokeapi.co/api/v2/pokemon?offset=0&limit=20"
    @GET("/api/v2/pokemon")
    suspend fun getPokemonResults(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 50,
    ): PokemonResultsDto

    //https://pokeapi.co/api/v2/pokemon/bulbasaur
    @GET("/api/v2/pokemon/{id}") //Pokemon name or list item position can be used for ID
    suspend fun getPokemonDetail(
        @Path("id") name: String
    ): PokemonDetailDto
}

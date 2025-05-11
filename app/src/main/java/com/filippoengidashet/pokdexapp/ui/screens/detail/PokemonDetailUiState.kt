package com.filippoengidashet.pokdexapp.ui.screens.detail

import com.filippoengidashet.pokdexapp.domain.model.PokemonDetail

data class PokemonDetailUiState(
    val isLoading: Boolean = false,
    val result: PokemonDetail? = null,
    val error: ErrorUiState? = null,
) {
    fun isFreshLoad() = result == null && error == null && !isLoading
    fun isFailed() = error != null && !isLoading
    fun getErrorMessage(alt: String) = error?.message ?: alt
}

data class ErrorUiState(
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)

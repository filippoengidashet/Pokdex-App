package com.filippoengidashet.pokdexapp.ui.screens.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.filippoengidashet.pokdexapp.domain.usecase.GetPokemonResultsUseCase
import com.filippoengidashet.pokdexapp.domain.model.PokemonItem

class PokeResultsPagingSource(
    private val getPokeListUseCase: GetPokemonResultsUseCase
) : PagingSource<Int, PokemonItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonItem> {
        val pageIndex = params.key ?: 0
        return try {

            val offset = pageIndex * PAGE_SIZE
            val limit = PAGE_SIZE

            val pokeResults = getPokeListUseCase(offset, limit)

            val prevKey = if (pokeResults.previous != null) pageIndex - 1 else null
            val nextKey = if (pokeResults.next != null) pageIndex + 1 else null

            LoadResult.Page(data = pokeResults.results, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGE_SIZE = 50
        const val PREFETCH_DISTANCE = 20
    }
}

package com.filippoengidashet.pokdexapp.ui.screens.list

import androidx.paging.AsyncPagingDataDiffer
import com.filippoengidashet.pokdexapp.Fixtures
import com.filippoengidashet.pokdexapp.domain.usecase.GetPokemonResultsUseCase
import com.filippoengidashet.pokdexapp.domain.model.PokemonItem
import com.filippoengidashet.pokdexapp.domain.model.PokemonResults
import com.filippoengidashet.pokdexapp.domain.usecase.AddFavouritePokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.GetAllFavouritesPokemonUseCase
import com.filippoengidashet.pokdexapp.domain.usecase.RemoveFavouritePokemonUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokeListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()//or UnconfinedTestDispatcher()

    @MockK
    private val getPokeListUseCase: GetPokemonResultsUseCase = mockk(relaxed = true)

    @MockK
    private val addFavouritePokemonUseCase: AddFavouritePokemonUseCase = mockk(relaxed = true)

    @MockK
    private val removeFavouritePokemonUseCase: RemoveFavouritePokemonUseCase = mockk(relaxed = true)

    @MockK
    private val getAllFavouritesPokemonUseCase =
        mockk<GetAllFavouritesPokemonUseCase>(relaxed = true)

    private lateinit var viewModel: PokeListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PokeListViewModel(
            getPokeListUseCase = getPokeListUseCase,
            addFavouritePokemonUseCase = addFavouritePokemonUseCase,
            removeFavouritePokemonUseCase = removeFavouritePokemonUseCase,
            getAllFavouritesPokemonUseCase = getAllFavouritesPokemonUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPagingData() = runTest {

        val expectedSize = 5

        val results = PokemonResults(
            count = expectedSize,
            next = null,
            previous = null,
            results = listOf(
                PokemonItem("Item-1", "url-1"),
                PokemonItem("Item-2", "url-2"),
                PokemonItem("Item-3", "url-3"),
                PokemonItem("Item-4", "url-4"),
                PokemonItem("Item-5", "url-5"),
            )
        )

        coEvery { getPokeListUseCase(0, 50) } returns results

        val differ = AsyncPagingDataDiffer(
            diffCallback = Fixtures.createDiffCallback<PokemonItem> { old, new ->
                old.name == new.name
            },
            updateCallback = Fixtures.noOpListCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher
        )

        val job = launch {
            viewModel.pagingData.collectLatest {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()

        val snapshot = differ.snapshot().items

        assertEquals(expectedSize, snapshot.size)
        assertEquals("Item-1", snapshot[0].name)
        assertEquals("Item-5", snapshot[4].name)

        job.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

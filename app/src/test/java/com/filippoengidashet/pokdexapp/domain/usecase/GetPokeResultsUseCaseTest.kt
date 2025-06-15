package com.filippoengidashet.pokdexapp.domain.usecase

import com.filippoengidashet.pokdexapp.domain.model.PokemonItem
import com.filippoengidashet.pokdexapp.domain.model.PokemonResults
import com.filippoengidashet.pokdexapp.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPokeResultsUseCaseTest {

    private val repository: PokemonRepository = mockk(relaxed = true)

    private lateinit var useCase: GetPokemonResultsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = GetPokemonResultsUseCase(repository)
    }

    @Test
    fun `getPokemonResults success`() = runTest {

        //GIVEN
        val mockResult = PokemonResults(
            count = 1,
            next = null,
            previous = null,
            results = listOf(PokemonItem(name = "Item-1", "url-1"))
        )
        coEvery { repository.getPokemonResults(any(), any()) } returns mockResult

        //WHEN
        val actualResult = useCase(0, 5)

        //THEN
        TestCase.assertEquals(mockResult, actualResult)
        coVerify(exactly = 1) { repository.getPokemonResults(0, 5) }
    }

    @Test(expected = RuntimeException::class)
    fun `getPokemonResults failure`() = runTest {
        //GIVEN
        val mockError = RuntimeException("no-network")
        coEvery { repository.getPokemonResults(any(), any()) } throws mockError

        //WHEN
        repository.getPokemonResults(0, 5)

        //THEN
        //won't reach here as it throw exception (unless wrapped with try-catch block).
        coVerify(exactly = 0) { repository.getPokemonResults(0, 5) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

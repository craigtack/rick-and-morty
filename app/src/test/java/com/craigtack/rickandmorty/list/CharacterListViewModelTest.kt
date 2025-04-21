package com.craigtack.rickandmorty.list

import com.craigtack.rickandmorty.network.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<CharacterRepository>()

    private val viewModel = CharacterListViewModel(repository)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `performSearch hits the API updates UI to success`() = runTest {
        coEvery { repository.getCharacters("r") } returns listOf()

        viewModel.performSearch("r")

        assertIs<CharacterListUiState.Success>(viewModel.uiState.value)
    }

    @Test
    fun `performSearch updates UI state to error when API fails`() = runTest {
        coEvery { repository.getCharacters(any()) } throws Exception()

        viewModel.performSearch("r")

        assertIs<CharacterListUiState.Error>(viewModel.uiState.value)
    }
}

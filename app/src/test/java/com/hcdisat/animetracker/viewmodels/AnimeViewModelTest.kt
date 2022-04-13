package com.hcdisat.animetracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.*
import com.hcdisat.animetracker.data.database.IDatabaseRepository
import com.hcdisat.animetracker.data.network.IApiRepository
import com.hcdisat.animetracker.models.episodes.EpisodesResponse
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import com.hcdisat.animetracker.viewmodels.state.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var instance: AnimeViewModel
    private val mockApiRepository = mockk<IApiRepository>(relaxed = true)
    private val mockDatabaseRepository = mockk<IDatabaseRepository>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        instance = AnimeViewModel(mockApiRepository, mockDatabaseRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get trending anime when trying to load from server returns loading state`() =
        runTest {
            instance.getTrending().observeForever {
                assertThat(it).isInstanceOf(AnimeState.LOADING::class.java)
            }
        }

    @Test
    fun `get trending anime when trying to load from server returns success state`() =
        runTest {

            coEvery { mockApiRepository.getTrending() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            coEvery { mockApiRepository.getPopular() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            coEvery { mockApiRepository.getRated() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            val listOfStates = mutableListOf<AnimeState>()

            instance.getTrending().observeForever {
                listOfStates.add(it)
            }

            listOfStates.filterIsInstance<AnimeState.SUCCESS>().also {
                assertThat(it).hasSize(3)
            }
        }

    @Test
    fun `get trending anime when trying to load from server returns error state`() =
        runTest {

            coEvery { mockApiRepository.getTrending() } returns
                    flowOf(AnimeState.ERROR(mockk()))

            coEvery { mockApiRepository.getPopular() } returns
                    flowOf(AnimeState.ERROR(mockk()))

            coEvery { mockApiRepository.getRated() } returns
                    flowOf(AnimeState.ERROR(mockk()))

            val listOfStates = mutableListOf<AnimeState>()

            instance.getTrending().observeForever {
                listOfStates.add(it)
            }

            listOfStates.filterIsInstance<AnimeState.ERROR>().also {
                assertThat(it).hasSize(3)
            }
        }

    @Test
    fun `get trending anime when trying to load from server a second time returns success state`() =
        runTest {

            coEvery { mockApiRepository.getTrending() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            coEvery { mockApiRepository.getPopular() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            coEvery { mockApiRepository.getRated() } returns
                    flowOf(AnimeState.SUCCESS(mockk(), mockk()))

            val listOfStates = mutableListOf<AnimeState>()

            // simulates the first call, it should load data from the api and point
            instance.getTrending().observeForever {
                listOfStates.add(it)
            }

            // simulates a second attempt to get the data, now it should return the cached anime state
            instance.getTrending().observeForever {
                listOfStates.add(it)
            }

            listOfStates.filterIsInstance<AnimeState.SUCCESS>().also {
                assertThat(it).hasSize(6)
            }
        }

    @Test
    fun `get anime episodes when trying to load from server returns loading state`() = runTest {
        val stateList = mutableListOf<UIState>()
        instance.getEpisodes("12").observeForever {
            stateList.add(it)
        }

        stateList.first().let {
            assertThat(it).isInstanceOf(UIState::class.java)
        }
    }

    @Test
    fun `get anime episodes when trying to load from server returns success state`() =
        runTest {
            coEvery { mockApiRepository.getEpisodes("66") } returns mockk {
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }

            val stateList = mutableListOf<UIState>()
            instance.getEpisodes("66").observeForever {
                stateList.add(it)
            }

            assertThat(stateList).hasSize(2)
            assertThat(stateList[0]).isInstanceOf(UIState.LOADING::class.java)
            assertThat(stateList[1]).isInstanceOf(UIState.SUCCESS::class.java)
        }
}
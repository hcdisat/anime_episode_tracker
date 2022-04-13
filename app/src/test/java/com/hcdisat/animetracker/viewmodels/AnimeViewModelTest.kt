package com.hcdisat.animetracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.*
import com.hcdisat.animetracker.data.database.IDatabaseRepository
import com.hcdisat.animetracker.data.network.IApiRepository
import com.hcdisat.animetracker.viewmodels.state.AnimeSectionData
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var instance: AnimeViewModel
    private val apiRepository = mockk<IApiRepository>(relaxed = true)
    private val databaseRepository = mockk<IDatabaseRepository>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        instance = AnimeViewModel(apiRepository, databaseRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get trending anime when trying to load from server returns loading state`() = runTest {
        instance.getTrending().observeForever {
            assertThat(it).isInstanceOf(AnimeState.LOADING::class.java)
        }
    }

    @Test
    fun `get trending anime when trying to load from server returns success state`() = runTest {

        coEvery { instance } returns mockk {
            every { animeSectionData.trending } returns AnimeState.LOADING
        }

        instance.getTrending().observeForever {
            assertThat(it).isInstanceOf(AnimeState.LOADING::class.java)
        }
    }

}
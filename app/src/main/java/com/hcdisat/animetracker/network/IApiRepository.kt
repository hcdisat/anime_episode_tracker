package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

interface IApiRepository {
    val animeFlow: StateFlow<AnimeState>
    suspend fun getTrending(coroutineScope: CoroutineScope)
}
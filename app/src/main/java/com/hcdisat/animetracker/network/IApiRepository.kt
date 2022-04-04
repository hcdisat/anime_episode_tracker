package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

interface IApiRepository {
    suspend fun getTrending(): Flow<AnimeState>
    suspend fun getPopular(page: Int = KitsuApi.PAGE_LIMIT): Flow<AnimeState>
    suspend fun getRated(page: Int = KitsuApi.PAGE_LIMIT): Flow<AnimeState>
}
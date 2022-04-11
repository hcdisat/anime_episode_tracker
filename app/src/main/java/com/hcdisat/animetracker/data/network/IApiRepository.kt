package com.hcdisat.animetracker.data.network

import com.hcdisat.animetracker.models.episodes.EpisodesResponse
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IApiRepository {
    suspend fun getTrending(): Flow<AnimeState>
    suspend fun getPopular(page: Int = KitsuApi.ANIME_PAGE_LIMIT): Flow<AnimeState>
    suspend fun getRated(page: Int = KitsuApi.ANIME_PAGE_LIMIT): Flow<AnimeState>
    suspend fun getEpisodes(id: String): Response<EpisodesResponse>
}
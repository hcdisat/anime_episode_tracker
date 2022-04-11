package com.hcdisat.animetracker.data.network

import com.hcdisat.animetracker.models.episodes.EpisodesResponse
import com.hcdisat.animetracker.ui.AnimeSections
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: KitsuApi
) : IApiRepository {

    /**
     * gets trending anime from server
     */
    override suspend fun getTrending(): Flow<AnimeState> =
        executeRequest(
            { api.getTrending() },
            {
                (it as AnimeState.SUCCESS).apply {
                    section = AnimeSections.TRENDING
                }
            }, {})

    override suspend fun getPopular(page: Int): Flow<AnimeState> =
        getAnime(AnimeSortBy.POPULAR.realName, page) {
            (it as AnimeState.SUCCESS).apply {
                section = AnimeSections.MOST_POPULAR
            }
        }

    override suspend fun getRated(page: Int): Flow<AnimeState> =
        getAnime(AnimeSortBy.MOST_RATED.realName) {
            (it as AnimeState.SUCCESS).apply {
                section = AnimeSections.MOST_RATED
            }
        }

    override suspend fun getEpisodes(id: String): Response<EpisodesResponse> = api.getEpisodes(id)


    private suspend fun getAnime(
        sortBy: String,
        page: Int = KitsuApi.ANIME_PAGE_LIMIT,
        success: (animeState: AnimeState) -> Unit
    ): Flow<AnimeState> = executeRequest({ api.getAnime(page, sortBy) }, { success(it) }) {}
}
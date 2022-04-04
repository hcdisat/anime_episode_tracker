package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.ui.AnimeSections
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                (it as AnimeState.SUCCESS<*>).apply {
                    section = AnimeSections.TRENDING
                }
            }, {})

    override suspend fun getPopular(page: Int): Flow<AnimeState> =
        getAnime(AnimeSortBy.POPULAR.realName, page){
            (it as AnimeState.SUCCESS<*>).apply {
                section = AnimeSections.MOST_POPULAR
            }
        }

    override suspend fun getRated(page: Int): Flow<AnimeState> =
        getAnime(AnimeSortBy.MOST_RATED.realName) {
            (it as AnimeState.SUCCESS<*>).apply {
                section = AnimeSections.MOST_RATED
            }
        }

    private suspend fun getAnime(
        sortBy: String,
        page: Int = KitsuApi.PAGE_LIMIT,
        success: (animeState: AnimeState) -> Unit
    ): Flow<AnimeState> = executeRequest({ api.getAnime(page, sortBy) }, { success(it) }) {}
}
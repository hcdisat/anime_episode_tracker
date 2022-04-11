package com.hcdisat.animetracker.data.network

import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.models.episodes.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KitsuApi {

    @GET(TRENDING_ANIME)
    suspend fun getTrending(): Response<AnimeResponse>

    @GET(ANIME)
    suspend fun getAnime(
        @Query("page[limit]") page: Int,
        @Query("sort") sortBy: String
    ): Response<AnimeResponse>

    @GET(EPISODES)
    suspend fun getEpisodes(
        @Path("id") id: String,
        @Query("page[limit]") page: Int = EPISODE_PAGE_LIMIT
    ): Response<EpisodesResponse>

    companion object {
        const val BASE_URL = "https://kitsu.io/api/edge/"
        private const val TRENDING_ANIME = "trending/anime"
        private const val ANIME = "anime"
        private const val EPISODES = "anime/{id}/episodes"

        const val ANIME_PAGE_LIMIT = 10
        const val EPISODE_PAGE_LIMIT = 20
    }
}
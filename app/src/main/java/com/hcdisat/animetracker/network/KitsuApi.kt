package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.models.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KitsuApi {

    @GET(TRENDING_ANIME)
    suspend fun getTrending(): Response<AnimeResponse>

    @GET(ANIME)
    suspend fun getAnime(
        @Query("page[limit]") page: Int,
        @Query("sort") sortBy: String
    ): Response<AnimeResponse>

    companion object {
        const val BASE_URL = "https://kitsu.io/api/edge/"
        private const val TRENDING_ANIME = "trending/anime"
        private const val ANIME = "anime"

        const val PAGE_LIMIT = 10
    }
}
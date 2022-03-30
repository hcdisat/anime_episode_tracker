package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.models.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET

interface KitsuApi {

    @GET(TRENDING_ANIME)
    suspend fun getTrending(): Response<AnimeResponse>

    companion object {
        const val BASE_URL = "https://kitsu.io/api/edge/"
        private const val TRENDING_ANIME = "trending/anime"
    }
}
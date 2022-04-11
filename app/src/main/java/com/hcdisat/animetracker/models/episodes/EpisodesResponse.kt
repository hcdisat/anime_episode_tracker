package com.hcdisat.animetracker.models.episodes


import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    @SerializedName("data")
    val episodes: List<Episode>,
    @SerializedName("links")
    val links: Links,
    @SerializedName("meta")
    val meta: MetaX
)
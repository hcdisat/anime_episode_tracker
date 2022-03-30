package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("data")
    val `data`: List<Anime>
)
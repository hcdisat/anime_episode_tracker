package com.hcdisat.animetracker.models.originals


import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("data")
    val `data`: List<Data>
)
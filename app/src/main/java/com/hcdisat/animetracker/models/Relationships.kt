package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Relationships(
    @SerializedName("episodes")
    val episodes: Episodes,
)
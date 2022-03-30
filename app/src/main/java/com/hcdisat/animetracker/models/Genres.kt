package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("links")
    val links: Links
)
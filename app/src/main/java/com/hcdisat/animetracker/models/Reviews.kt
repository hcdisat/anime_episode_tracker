package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("links")
    val links: Links
)
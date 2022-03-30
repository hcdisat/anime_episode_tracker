package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Episodes(
    @SerializedName("links")
    val links: Links
)
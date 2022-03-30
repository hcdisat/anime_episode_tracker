package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class StreamingLinks(
    @SerializedName("links")
    val links: Links
)
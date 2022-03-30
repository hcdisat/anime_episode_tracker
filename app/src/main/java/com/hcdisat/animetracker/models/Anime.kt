package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("attributes")
    val attributes: Attributes,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: Links,
    @SerializedName("type")
    val type: String
)
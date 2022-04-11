package com.hcdisat.animetracker.models.episodes


import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("attributes")
    val attributes: Attributes,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: Links,
    @SerializedName("type")
    val type: String
)
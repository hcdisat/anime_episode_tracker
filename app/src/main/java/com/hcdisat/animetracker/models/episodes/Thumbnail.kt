package com.hcdisat.animetracker.models.episodes


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("original")
    val original: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("tiny")
    val tiny: String
)
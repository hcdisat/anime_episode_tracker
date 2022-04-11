package com.hcdisat.animetracker.models.episodes


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("related")
    val related: String,
    @SerializedName("self")
    val self: String
)
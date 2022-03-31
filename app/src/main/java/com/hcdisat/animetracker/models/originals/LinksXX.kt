package com.hcdisat.animetracker.models.originals


import com.google.gson.annotations.SerializedName

data class LinksXX(
    @SerializedName("related")
    val related: String,
    @SerializedName("self")
    val self: String
)
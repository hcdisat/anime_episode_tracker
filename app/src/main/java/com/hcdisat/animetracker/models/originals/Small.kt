package com.hcdisat.animetracker.models.originals


import com.google.gson.annotations.SerializedName

data class Small(
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)
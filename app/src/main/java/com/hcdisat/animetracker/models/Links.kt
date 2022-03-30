package com.hcdisat.animetracker.models


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: String
)
package com.hcdisat.animetracker.models.originals


import com.google.gson.annotations.SerializedName

data class OriginalResponse(
    @SerializedName("data")
    val `data`: List<Data>
)
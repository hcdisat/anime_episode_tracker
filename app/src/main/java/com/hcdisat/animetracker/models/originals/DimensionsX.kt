package com.hcdisat.animetracker.models.originals


import com.google.gson.annotations.SerializedName

data class DimensionsX(
    @SerializedName("large")
    val large: LargeX,
    @SerializedName("medium")
    val medium: Medium,
    @SerializedName("small")
    val small: SmallX,
    @SerializedName("tiny")
    val tiny: TinyX
)
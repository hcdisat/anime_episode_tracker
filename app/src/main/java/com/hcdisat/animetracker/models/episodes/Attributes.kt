package com.hcdisat.animetracker.models.episodes


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("airdate")
    val airdate: String,
    @SerializedName("canonicalTitle")
    val canonicalTitle: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("length")
    val length: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("relativeNumber")
    val relativeNumber: Any,
    @SerializedName("seasonNumber")
    val seasonNumber: Int,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("titles")
    val titles: Titles,
    @SerializedName("updatedAt")
    val updatedAt: String
)
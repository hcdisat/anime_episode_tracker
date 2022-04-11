package com.hcdisat.animetracker.models.transformers

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class AnimeTransformer(
    @PrimaryKey val animeId: String,
    var titleEn: String?,
    var titleJa: String?,
    var ratingRank: Int?,
    var ageRatingGuide: String?,
    var description: String?,
    var status: String?
)
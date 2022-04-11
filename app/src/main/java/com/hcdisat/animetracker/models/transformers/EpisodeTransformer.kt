package com.hcdisat.animetracker.models.transformers

import androidx.room.*

@Entity(
    tableName = "episodes",
    foreignKeys = [ForeignKey(
        entity = AnimeTransformer::class,
        parentColumns = arrayOf("animeId"),
        childColumns = arrayOf("animeId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class EpisodeTransformer(
    @PrimaryKey val id: String,
    @ColumnInfo(index = true) val animeId: String,
    val thumbnail: String?,
    val title: String?,
    val seasonNumber: Int?,
    val duration: Int?
)

data class AnimeAndEpisodes(
    @Embedded
    val anime: AnimeTransformer,

    @Relation(
        parentColumn = "animeId",
        entityColumn = "animeId"
    )
    val episodes: List<EpisodeTransformer>
)
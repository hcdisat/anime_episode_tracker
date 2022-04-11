package com.hcdisat.animetracker.data.database

import androidx.room.*
import androidx.room.Database
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.models.transformers.AnimeTransformer
import com.hcdisat.animetracker.models.transformers.EpisodeTransformer
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [
        AnimeTransformer::class,
        EpisodeTransformer::class
    ],
    version = DB_VERSION
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}

@Dao
interface AnimeDao {
    @Transaction
    @Query("SELECT * FROM animes")
    fun getAll(): Flow<List<AnimeAndEpisodes>>

    @Transaction
    @Query("SELECT * FROM animes WHERE animeId = :animeId")
    fun getAnimeById(animeId: String): Flow<AnimeAndEpisodes>

    @Insert(entity = AnimeTransformer::class, onConflict = OnConflictStrategy.REPLACE)
    fun save(anime: AnimeTransformer)

    @Insert(entity = EpisodeTransformer::class, onConflict = OnConflictStrategy.REPLACE)
    fun save(episodes: List<EpisodeTransformer>)

    @Delete(entity = AnimeTransformer::class)
    fun deleteAnime(animeTransformer: AnimeTransformer)
}

const val DB_VERSION = 1
const val DB_NAME = "anime_db"
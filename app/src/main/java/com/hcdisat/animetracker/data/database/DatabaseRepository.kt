package com.hcdisat.animetracker.data.database

import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.models.transformers.AnimeTransformer
import com.hcdisat.animetracker.models.transformers.EpisodeTransformer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface IDatabaseRepository {
    fun getAll(): Flow<List<AnimeAndEpisodes>>
    fun getAnimeById(animeId: String): Flow<AnimeAndEpisodes?>
    fun save(episodes: List<EpisodeTransformer>): Flow<Unit>
    fun save(anime: AnimeTransformer): Flow<Unit>
    fun deleteAnime(animeTransformer: AnimeTransformer): Flow<Unit>
}

class DatabaseRepository @Inject constructor(
    private val animeDao: AnimeDao
) : IDatabaseRepository {
    override fun getAll() = animeDao.getAll()

    override fun getAnimeById(animeId: String) =
        animeDao.getAnimeById(animeId)

    override fun save(episodes: List<EpisodeTransformer>) =
        flow { emit(animeDao.save(episodes)) }

    override fun save(anime: AnimeTransformer) = flow { emit(animeDao.save(anime)) }

    override fun deleteAnime(animeTransformer: AnimeTransformer) =
        flow { emit(animeDao.deleteAnime(animeTransformer)) }
}
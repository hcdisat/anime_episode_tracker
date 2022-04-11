package com.hcdisat.animetracker.models.transformers

import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.episodes.Attributes
import com.hcdisat.animetracker.models.episodes.Episode

fun Anime.episodeLink(): String =
    relationships.episodes.links.related

fun Attributes.episodeName(): String =
    "E$number $canonicalTitle"

package com.hcdisat.animetracker.viewmodels.state

import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.AnimeResponse

sealed class AnimeState {
    object LOADING: AnimeState()
    class SUCCESS(response: List<Anime>): AnimeState()
    class ERROR(throwable: Throwable): AnimeState()
}
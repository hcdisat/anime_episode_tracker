package com.hcdisat.animetracker.viewmodels.state

import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.ui.AnimeSections

sealed class AnimeState {
    object LOADING: AnimeState()
    object DEFAULT: AnimeState()
    class SUCCESS<T>(var section: AnimeSections = AnimeSections.TRENDING, val response: T): AnimeState()
    class ERROR(val throwable: Throwable): AnimeState()
}
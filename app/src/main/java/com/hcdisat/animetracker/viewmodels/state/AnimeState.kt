package com.hcdisat.animetracker.viewmodels.state

import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.ui.AnimeSections

sealed class AnimeState {
    object LOADING : AnimeState()
    class SUCCESS(
        var section: AnimeSections =
            AnimeSections.TRENDING, val response: AnimeResponse
    ) : AnimeState()

    class ERROR(val throwable: Throwable) : AnimeState()
}

class AnimeSectionData(
    var trending: AnimeState = AnimeState.LOADING,
    var popular: AnimeState = AnimeState.LOADING,
    var rated: AnimeState = AnimeState.LOADING
) {

}
package com.hcdisat.animetracker.viewmodels.state

import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.ui.AnimeSections

sealed class AnimeState {
    object LOADING : AnimeState()
    class SUCCESS(
        var section: AnimeSections =
            AnimeSections.TRENDING, val response: AnimeResponse
    ) : AnimeState()

    class ERROR(val throwable: Throwable) : AnimeState()
}

sealed interface UIState {
    object LOADING : UIState
    class SUCCESS<T>(val response: T) : UIState
    class ERROR(val throwable: Throwable) : UIState
}

class AnimeSectionData(
    var trending: AnimeState = AnimeState.LOADING,
    var popular: AnimeState = AnimeState.LOADING,
    var rated: AnimeState = AnimeState.LOADING
)

sealed interface DBOperationsState {
    object REMOVED : DBOperationsState
    object SAVED : DBOperationsState
    object LOADING : DBOperationsState
    object ERROR : DBOperationsState
    class RESULT_SET(val animes: List<AnimeAndEpisodes>) : DBOperationsState
}
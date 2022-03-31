package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ApiRepository @Inject constructor (
    private val api: KitsuApi
) : IApiRepository {

    private var _animeFlow: MutableStateFlow<AnimeState> =
        MutableStateFlow(AnimeState.LOADING)
    override val animeFlow: StateFlow<AnimeState> get() = _animeFlow

    /**
     * gets trending anime from server
     *
     */
    override suspend fun getTrending(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            try {
                val response = api.getTrending()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _animeFlow.value = AnimeState.SUCCESS(it.data)
                    }
                } else {
                    throw Exception("Something went wrong with the request")
                }

            } catch (e: Exception) {
                _animeFlow.value = AnimeState.ERROR(e)
            }
        }
    }
}
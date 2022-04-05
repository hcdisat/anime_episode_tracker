package com.hcdisat.animetracker.network

import com.hcdisat.animetracker.models.AnimeResponse
import com.hcdisat.animetracker.ui.AnimeSections
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

inline fun IApiRepository.executeRequest(
    crossinline resource: suspend () -> Response<AnimeResponse>,
     crossinline success: (state: AnimeState) -> Unit,
    crossinline error: (throwable: Throwable) -> Unit
): Flow<AnimeState> = flow {
    try {
        val response = resource()
        if (response.isSuccessful) {
            response.body()?.let {
                val state = AnimeState.SUCCESS(response = it)
                success(state)
                emit(state)
            }
        } else
            throw Exception("Something went wrong with the request")
    } catch (e: Exception) {
        emit(AnimeState.ERROR(e))
        error(e)
    }
}
package com.hcdisat.animetracker.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.hcdisat.animetracker.TAG
import com.hcdisat.animetracker.data.database.IDatabaseRepository
import com.hcdisat.animetracker.di.DispatchersModule.IODispatcher
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.data.network.IApiRepository
import com.hcdisat.animetracker.models.transformers.AnimeAndEpisodes
import com.hcdisat.animetracker.viewmodels.state.AnimeSectionData
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import com.hcdisat.animetracker.viewmodels.state.DBOperationsState
import com.hcdisat.animetracker.viewmodels.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _state: MutableLiveData<AnimeState> =
        MutableLiveData(AnimeState.LOADING)
    val state: LiveData<AnimeState> get() = _state

    private val _animeSectionData: AnimeSectionData = AnimeSectionData()

    var selectedAnime: Anime? = null

    var playAction: () -> Unit = {}

    fun getTrending(): LiveData<AnimeState> = liveData(ioDispatcher) {
        emit(AnimeState.LOADING)

        when (_animeSectionData.trending) {
            is AnimeState.LOADING -> {
                apiRepository.getTrending().collect {
                    _animeSectionData.trending = it
                    emit(it)
                }
            }
            else -> emit(_animeSectionData.trending)
        }

        when (_animeSectionData.popular) {
            is AnimeState.LOADING -> {
                apiRepository.getPopular().collect {
                    _animeSectionData.popular = it
                    emit(it)
                }
            }
            else -> emit(_animeSectionData.popular)
        }

        when (_animeSectionData.rated) {
            is AnimeState.LOADING -> {
                apiRepository.getRated().collect {
                    _animeSectionData.rated = it
                    emit(it)
                }
            }
            else -> emit(_animeSectionData.rated)
        }
    }

    fun getEpisodes(id: String): LiveData<UIState> = liveData(ioDispatcher) {
        try {
            val response = apiRepository.getEpisodes(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.Main) {
                        emit(UIState.SUCCESS(response = it))
                    }
                }
            } else
                throw Exception()
        } catch (e: Exception) {
            Log.d(TAG, "getEpisodes: ${e.localizedMessage}")
            emit(UIState.ERROR(e))
        }
    }

    fun saveAnime(
        animeAndEpisodes: AnimeAndEpisodes
    ): Flow<DBOperationsState> = flow {
        viewModelScope.launch(ioDispatcher) {
            databaseRepository.getAnimeById(animeAndEpisodes.anime.animeId).collect { anime ->
                anime?.let {
                    databaseRepository.deleteAnime(animeAndEpisodes.anime).collect {
                        withContext(Dispatchers.Main) {
                            emit(DBOperationsState.REMOVED)
                        }
                    }
                } ?: databaseRepository.save(animeAndEpisodes.anime).collect {
                    databaseRepository.save(animeAndEpisodes.episodes).collect {
                        withContext(Dispatchers.Main) {
                            emit(DBOperationsState.SAVED)
                        }
                    }
                }
            }
        }
    }
}
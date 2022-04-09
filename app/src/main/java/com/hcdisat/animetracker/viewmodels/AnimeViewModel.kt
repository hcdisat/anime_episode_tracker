package com.hcdisat.animetracker.viewmodels

import androidx.lifecycle.*
import com.hcdisat.animetracker.di.DispatchersModule.IODispatcher
import com.hcdisat.animetracker.models.Anime
import com.hcdisat.animetracker.network.IApiRepository
import com.hcdisat.animetracker.viewmodels.state.AnimeSectionData
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val apiRepository: IApiRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _state: MutableLiveData<AnimeState> =
        MutableLiveData(AnimeState.LOADING)
    val state: LiveData<AnimeState> get() = _state

    private var _animeSectionData: AnimeSectionData = AnimeSectionData()
    val animeSectionData: AnimeSectionData get() = _animeSectionData

    var selectedAnime: Anime? = null

    fun getTrending() {
//        liveData<Int> {
//            val foo = Tri
//        }
        _state.value = AnimeState.LOADING
        viewModelScope.launch(ioDispatcher) {

            apiRepository.getTrending().collect {
                _state.postValue(it)
                _animeSectionData.trending = it
            }

            apiRepository.getPopular().collect {
                _state.postValue(it)
                _animeSectionData.popular = it
            }

            apiRepository.getRated().collect {
                _state.postValue(it)
                _animeSectionData.rated = it
            }
        }
    }
}
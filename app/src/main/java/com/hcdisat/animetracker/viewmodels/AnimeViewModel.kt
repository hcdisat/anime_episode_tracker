package com.hcdisat.animetracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.animetracker.di.DispatchersModule.IODispatcher
import com.hcdisat.animetracker.network.IApiRepository
import com.hcdisat.animetracker.viewmodels.state.AnimeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
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

    @FlowPreview
    fun getTrending() {
        resetState()
        viewModelScope.launch(ioDispatcher) {
            apiRepository.getTrending().collect { _state.postValue(it) }
            apiRepository.getPopular().collect { _state.postValue(it) }
            apiRepository.getRated().collect { _state.postValue(it) }
        }
    }

    private fun resetState() {
        _state.value = AnimeState.LOADING
    }
}
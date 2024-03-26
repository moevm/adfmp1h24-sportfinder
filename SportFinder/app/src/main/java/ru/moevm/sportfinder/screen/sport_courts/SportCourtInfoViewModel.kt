package ru.moevm.sportfinder.screen.sport_courts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.domain.use_case.GetSportCourtByIdUseCase
import javax.inject.Inject

@HiltViewModel
class SportCourtInfoViewModel @Inject constructor(
    private val getSportCourtByIdUseCase: GetSportCourtByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sportCourtId = savedStateHandle.get<Int>("sportCourtId")

    private val _state = MutableStateFlow(SportCourtInfoState())
    val state = _state.asStateFlow()

    private val _isShareUrlClicked = MutableStateFlow(false)
    val isShareUrlClicked = _isShareUrlClicked.asStateFlow()

    init {
        loadSportCourtInfo()
    }

    fun onShareClick() {
        _isShareUrlClicked.value = true
    }

    fun onSharedUrlShown() {
        _isShareUrlClicked.value = false
    }

    fun onFavoritesClicked() {
        _state.value = _state.value.copy(
            isFavorite = !_state.value.isFavorite
        )
    }

    private fun loadSportCourtInfo() {
        sportCourtId?.let {
            getSportCourtByIdUseCase(sportCourtId)
                .flowOn(Dispatchers.IO)
                .onEach { newState ->
                    val sportCourtInfoState = if (newState == null) {
                        SportCourtInfoState(
                            courtName = "Ошибка",
                            courtAddress = "",
                            courtInitialPoint = null,
                            courtDistance = 0.0,
                            courtWeatherTemperature = 0,
                            courtAmountFavorites = 0,
                            isFavorite = false,
                            courtTags = persistentListOf(),
                            courtInfo = "Ошибка получения данных",
                            courtLinkUrl = ""
                        )
                    } else {
                        SportCourtInfoState(
                            courtName = newState.name,
                            courtAddress = newState.address,
                            courtInitialPoint = newState.coordinates,
                            courtDistance = 0.0,
                            courtWeatherTemperature = 0,
                            courtAmountFavorites = 0,
                            isFavorite = false,
                            courtTags = newState.tags.toPersistentList(),
                            courtInfo = newState.info,
                            courtLinkUrl = ""
                        )
                    }
                    _state.value = sportCourtInfoState
                }
                .launchIn(viewModelScope)
        }
    }
}
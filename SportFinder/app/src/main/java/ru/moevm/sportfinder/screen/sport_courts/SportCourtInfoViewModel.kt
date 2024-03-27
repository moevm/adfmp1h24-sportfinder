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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.sharing.SharingTextGenerator
import ru.moevm.sportfinder.domain.use_case.GetSportCourtByIdUseCase
import ru.moevm.sportfinder.domain.use_case.GetWeatherTemperatureUseCase
import javax.inject.Inject

@HiltViewModel
class SportCourtInfoViewModel @Inject constructor(
    private val getSportCourtByIdUseCase: GetSportCourtByIdUseCase,
    private val sharingTextGenerator: SharingTextGenerator,
    private val getWeatherTemperatureUseCase: GetWeatherTemperatureUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sportCourtId = savedStateHandle.get<Int>("sportCourtId")

    private val _state = MutableStateFlow(SportCourtInfoState())
    val state = _state.asStateFlow()

    init {
        loadSportCourtInfo()
    }

    fun getSharingText(): String {
        return sharingTextGenerator.fromSportCourtInfo(
            _state.value.courtName,
            _state.value.courtAddress,
            _state.value.courtTags.toList(),
            _state.value.courtInfo
        )
    }

    fun onFavoritesClicked() {
        _state.value = _state.value.copy(
            isFavorite = !_state.value.isFavorite
        )
    }

    private fun loadSportCourtInfo() {
        sportCourtId?.let {

            flow {
                val sportCourt = getSportCourtByIdUseCase(sportCourtId).first()
                val sportCourtInfoState = if (sportCourt == null) {
                    SportCourtInfoState(
                        courtName = "Ошибка",
                        courtAddress = "",
                        courtInitialPoint = null,
                        courtTemperature = 0,
                        courtAmountFavorites = 0,
                        isFavorite = false,
                        courtTags = persistentListOf(),
                        courtInfo = "Ошибка получения данных",
                    )
                } else {
                    val temperature = getWeatherTemperatureUseCase(sportCourt.coordinates).first()
                    SportCourtInfoState(
                        courtName = sportCourt.name,
                        courtAddress = sportCourt.address,
                        courtInitialPoint = sportCourt.coordinates,
                        courtTemperature = temperature,
                        courtAmountFavorites = 0,
                        isFavorite = false,
                        courtTags = sportCourt.tags.toPersistentList(),
                        courtInfo = sportCourt.info,
                    )
                }
                emit(sportCourtInfoState)
            }
                .flowOn(Dispatchers.IO)
                .onEach { sportCourtInfoState ->
                    _state.value = sportCourtInfoState
                }
                .launchIn(viewModelScope)
        }
    }
}
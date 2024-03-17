package ru.moevm.sportfinder.screen.sport_courts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.common.Constants
import javax.inject.Inject

@HiltViewModel
class SportCourtInfoViewModel @Inject constructor() : ViewModel() {

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
        flow {
            emit(
                SportCourtInfoState(
                    courtName = "SPOT NAME PLACEHOLDER",
                    courtAddress = "ул. Школьная",
                    courtInitialPoint = Constants.SPB_CENTER_POINT,
                    courtDistance = 1.2,
                    courtWeatherTemperature = 6,
                    courtAmountFavorites = 5,
                    isFavorite = false,
                    courtTags = persistentListOf("Workout", "Бег"),
                    courtInfo = "Placeholder for this spot. Mb the best spot from all over the world! Idk really.",
                    courtLinkUrl = "https://sportfinder.com",
                )
            )
        }
            .onEach { newState ->
                _state.value = newState
            }
            .launchIn(viewModelScope)
    }
}
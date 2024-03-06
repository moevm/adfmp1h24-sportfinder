package ru.moevm.sportfinder.screen.sport_courts

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.moevm.sportfinder.common.Constants
import javax.inject.Inject

@HiltViewModel
class SportCourtMapScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SportCourtMapState())
    val state = _state.asStateFlow()


    val startPoint = Constants.SPB_CENTER_POINT

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        // TODO: пока нечего фильтровать
    }
}
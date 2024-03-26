package ru.moevm.sportfinder.screen.sport_courts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.common.Constants
import ru.moevm.sportfinder.domain.use_case.GetSportCourtsUseCase
import javax.inject.Inject

@HiltViewModel
class SportCourtMapViewModel @Inject constructor(
    private val getSportCourtsUseCase: GetSportCourtsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SportCourtMapState())
    val state = _state.asStateFlow()


    val startPoint = Constants.SPB_CENTER_POINT

    init {
        updateListOfSportCourt()
    }

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        updateListOfSportCourt(_state.value.textForFilter.lowercase())
    }

    private fun updateListOfSportCourt(filter: String = "") {
        getSportCourtsUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                val filteredData = if (filter.isNotBlank()) {
                    newData.filter { filter in it.name }
                } else {
                    newData
                }
                val listOfSportCourt = filteredData.map { sportCourtVO ->
                    SportCourtMapItem(
                        courtId = sportCourtVO.id,
                        name = sportCourtVO.name,
                        coordinates = sportCourtVO.coordinates
                    )
                }.toPersistentList()
                _state.value = _state.value.copy(listOfSportCourt = listOfSportCourt)
            }
            .launchIn(viewModelScope)
    }
}
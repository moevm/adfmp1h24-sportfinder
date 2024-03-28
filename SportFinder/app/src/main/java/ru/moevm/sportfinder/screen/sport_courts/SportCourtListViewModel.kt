package ru.moevm.sportfinder.screen.sport_courts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.GetSportCourtsUseCase
import ru.moevm.sportfinder.domain.use_case.GetWeatherTemperaturesUseCase
import javax.inject.Inject

@HiltViewModel
class SportCourtListViewModel @Inject constructor(
    private val getSportCourtsUseCase: GetSportCourtsUseCase,
    private val getWeatherTemperaturesUseCase: GetWeatherTemperaturesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SportCourtListState())
    val state = _state.asStateFlow()

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
        _state.value = _state.value.copy(isLoading = true)

        flow {
            val sportCourts = getSportCourtsUseCase().first()
            val filteredData = if (filter.isNotBlank()) {
                sportCourts.filter { filter in it.name.lowercase() }
            } else {
                sportCourts
            }
            val temperatures = getWeatherTemperaturesUseCase(filteredData.map { it.coordinates }).first()
            val listOfSportCourt = filteredData.mapIndexed { index, sportCourtVO ->
                SportCourtListItem(
                    courtId = sportCourtVO.id,
                    name = sportCourtVO.name,
                    tags = sportCourtVO.tags.toPersistentList(),
                    temperature = temperatures[index]
                )
            }.toPersistentList()
            emit(listOfSportCourt)
        }
            .flowOn(Dispatchers.IO)
            .onEach { listOfSportCourt ->
                _state.value = _state.value.copy(listOfSportCourt = listOfSportCourt, isLoading = false)
            }
            .launchIn(viewModelScope)
    }
}
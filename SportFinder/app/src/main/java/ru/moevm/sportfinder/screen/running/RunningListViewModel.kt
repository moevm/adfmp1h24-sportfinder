package ru.moevm.sportfinder.screen.running

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
import ru.moevm.sportfinder.domain.use_case.GetWeatherTemperaturesUseCase
import ru.moevm.sportfinder.domain.use_case.UseRunningDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class RunningListViewModel @Inject constructor(
    private val useRunningDatabaseUseCase: UseRunningDatabaseUseCase,
    private val getWeatherTemperaturesUseCase: GetWeatherTemperaturesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RunningListState())
    val state = _state.asStateFlow()

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        updateListOfRunning(_state.value.textForFilter)
    }

    fun updateListOfRunning(filter: String = "") {
        _state.value = _state.value.copy(isLoading = true)

        flow {
            val runningsDto = useRunningDatabaseUseCase.getRunnings().first()
            val filteredData = if (filter.isNotBlank()) {
                runningsDto.filter { filter in it.title }
            } else {
                runningsDto
            }
            val temperatures = getWeatherTemperaturesUseCase(filteredData.map { it.points.first() }).first()
            val newRunnings = filteredData.mapIndexed { index, runningDTO ->
                RunningListItemVO(
                    runningId = runningDTO.id,
                    name = runningDTO.title,
                    tags = runningDTO.tags.toPersistentList(),
                    distance = runningDTO.dist,
                    temperature = temperatures[index]
                ) }.toPersistentList()
            emit(newRunnings)
        }
            .flowOn(Dispatchers.IO)
            .onEach { newRunnings ->
                _state.value = _state.value.copy(
                    listOfRunning = newRunnings,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}
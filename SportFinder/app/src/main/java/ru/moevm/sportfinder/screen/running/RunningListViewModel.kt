package ru.moevm.sportfinder.screen.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.use_case.UseRunningDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class RunningListViewModel @Inject constructor(
    private val useRunningDatabaseUseCase: UseRunningDatabaseUseCase,
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

        useRunningDatabaseUseCase.getRunnings()
            .onEach { runnings ->
                val filteredData = if (filter.isNotBlank()) {
                    runnings.filter { filter in it.title }
                } else {
                    runnings
                }
                val newRunnings = filteredData.map { running ->
                    RunningListItemVO(
                        runningId = running.id,
                        name = running.title,
                        tags = running.tags.toPersistentList(),
                        distance = running.dist,
                        temperature = 6
                ) }.toPersistentList()

                _state.value = _state.value.copy(
                    listOfRunning = newRunnings,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}
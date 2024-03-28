package ru.moevm.sportfinder.screen.training

import android.util.Log
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
import ru.moevm.sportfinder.domain.use_case.UseTrainingDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val useTrainingDatabaseUseCase: UseTrainingDatabaseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrainingListState())
    val state = _state.asStateFlow()

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        updateListOfTrainings(_state.value.textForFilter.lowercase())
    }

    fun updateListOfTrainings(filter: String = "") {
        _state.value = _state.value.copy(isLoading = true)

        useTrainingDatabaseUseCase.getTrainings()
            .onEach { trainingDTOS ->
                val filteredData = if (filter.isNotBlank()) {
                    trainingDTOS.filter { filter in it.name.lowercase() }
                } else {
                    trainingDTOS
                }
                _state.value = _state.value.copy(
                    listOfTraining = filteredData.map { trainingDTO ->
                        TrainingListItemVO(
                            trainingId = trainingDTO.id,
                            name = trainingDTO.name,
                            tags = trainingDTO.tags.toPersistentList(),
                        )
                    }.toPersistentList(),
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}
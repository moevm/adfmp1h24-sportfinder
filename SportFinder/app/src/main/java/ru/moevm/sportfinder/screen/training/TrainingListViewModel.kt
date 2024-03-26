package ru.moevm.sportfinder.screen.training

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
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TrainingListState())
    val state = _state.asStateFlow()

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        // TODO: пока нечего фильтровать
    }

    fun onItemClicked(trainingId: Long) {

    }

    fun updateListOfTrainings() {
        flow {
            _state.value = _state.value.copy(isLoading = true)
            val fakeData = getFakeData().toPersistentList()
            emit(fakeData)
        }
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                _state.value = _state.value.copy(listOfTraining = newData, isLoading = false)
            }
            .launchIn(viewModelScope)
    }

    private fun getFakeData() = listOf(
        TrainingListItemVO(
            trainingId = 0,
            name = "Беговая тренировка",
            tags = persistentListOf("Бег", "Занятия"),
            temperature = 6f,
        ),
        TrainingListItemVO(
            trainingId = 1,
            name = "Скакалка",
            tags = persistentListOf("Асфальт", "Занятия", "Тренажёры"),
            temperature = 3f,
        ),
        TrainingListItemVO(
            trainingId = 2,
            name = "На выносливость, свежий воздух",
            tags = persistentListOf("Занятия", "Деревья"),
            temperature = 5f,
        ),
        TrainingListItemVO(
            trainingId = 3,
            name = "Занятия на стадионе",
            tags = persistentListOf("Асфальт", "Занятия"),
            temperature = 4f,
        ),
    )
}
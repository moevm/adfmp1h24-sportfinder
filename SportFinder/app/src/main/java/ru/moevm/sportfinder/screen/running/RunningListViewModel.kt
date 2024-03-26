package ru.moevm.sportfinder.screen.running

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
class RunningListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RunningListState())
    val state = _state.asStateFlow()

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        // TODO: пока нечего фильтровать
    }

    fun onItemClicked(runningId: Long) {

    }

    fun updateListOfRunning() {
        flow {
            _state.value = _state.value.copy(isLoading = true)
            val fakeData = getFakeData().toPersistentList()
            emit(fakeData)
        }
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                _state.value = _state.value.copy(listOfRunning = newData, isLoading = false)
            }
            .launchIn(viewModelScope)
    }

    private fun getFakeData() = listOf(
        RunningListItemVO(
            runningId = 0,
            name = "Старая деревня",
            tags = persistentListOf("Деревья", "Бездорожье"),
            distance = 1.6f,
            temperature = 6f,
        ),
        RunningListItemVO(
            runningId = 1,
            name = "Пионерский парк",
            tags = persistentListOf("Асфальт", "Деревья"),
            distance = 2.3f,
            temperature = 3f,
        ),
        RunningListItemVO(
            runningId = 2,
            name = "Октябрьский парк",
            tags = persistentListOf("Деревья"),
            distance = 2f,
            temperature = 5f,
        ),
        RunningListItemVO(
            runningId = 3,
            name = "Стадион у школы",
            tags = persistentListOf("Асфальт"),
            distance = 3.9f,
            temperature = 4f,
        ),
    )
}
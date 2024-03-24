package ru.moevm.sportfinder.screen.sport_courts

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
class SportCourtListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SportCourtListState())
    val state = _state.asStateFlow()

    init {
        updateListOfSportCourt()
    }

    fun onTextForFilterChanged(newFilter: String) {
        _state.value = _state.value.copy(textForFilter = newFilter)
    }

    fun onFilterApply() {
        // TODO: пока нечего фильтровать
    }

    fun onItemClicked(courtId: Long) {

    }

    private fun updateListOfSportCourt() {
        flow {
            _state.value = _state.value.copy(isLoading = true)
            val fakeData = getFakeData().toPersistentList()
            emit(fakeData)
        }
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                _state.value = _state.value.copy(listOfSportCourt = newData, isLoading = false)
            }
            .launchIn(viewModelScope)
    }

    private fun getFakeData() = listOf(
        SportCourtListItemVO(
            courtId = 0,
            name = "Старая деревня",
            tags = persistentListOf("Бег", "Занятия"),
            temperature = 6f,
        ),
        SportCourtListItemVO(
            courtId = 1,
            name = "Пионерский парк",
            tags = persistentListOf("Асфальт", "Занятия", "Тренажёры"),
            temperature = 3f,
        ),
        SportCourtListItemVO(
            courtId = 2,
            name = "Октябрьский парк",
            tags = persistentListOf("Занятия", "Деревья"),
            temperature = 5f,
        ),
        SportCourtListItemVO(
            courtId = 3,
            name = "Стадион у школы",
            tags = persistentListOf("Асфальт", "Занятия"),
            temperature = 4f,
        ),
    )
}
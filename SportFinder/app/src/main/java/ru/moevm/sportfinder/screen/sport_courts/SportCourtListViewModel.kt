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
import ru.moevm.sportfinder.domain.use_case.GetSportCourtsUseCase
import javax.inject.Inject

@HiltViewModel
class SportCourtListViewModel @Inject constructor(
    private val getSportCourtsUseCase: GetSportCourtsUseCase
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

        getSportCourtsUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { newData ->
                val filteredData = if (filter.isNotBlank()) {
                    newData.filter { filter in it.name }
                } else {
                    newData
                }
                val listOfSportCourt = filteredData.map { sportCourtVO ->
                    SportCourtListItem(
                        courtId = sportCourtVO.id,
                        name = sportCourtVO.name,
                        tags = sportCourtVO.tags.toPersistentList(),
                        6f // TODO: Сделать загрузку погоды
                    )
                }.toPersistentList()
                _state.value = _state.value.copy(listOfSportCourt = listOfSportCourt, isLoading = false)
            }
            .launchIn(viewModelScope)
    }
}
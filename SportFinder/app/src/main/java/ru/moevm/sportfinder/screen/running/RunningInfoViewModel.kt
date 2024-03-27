package ru.moevm.sportfinder.screen.running

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.moevm.sportfinder.domain.sharing.SharingTextGenerator
import ru.moevm.sportfinder.domain.use_case.UseRunningDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class RunningInfoViewModel @Inject constructor(
    private val useRunningDatabaseUseCase: UseRunningDatabaseUseCase,
    private val sharingTextGenerator: SharingTextGenerator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val runningId = savedStateHandle.get<Int>("runningId")

    private val _state = MutableStateFlow(RunningInfoState())
    val state = _state.asStateFlow()

    fun getSharingText(): String {
        return sharingTextGenerator.fromRunningInfo(
            _state.value.title,
            _state.value.distance,
            _state.value.listOfTags.toList()
        )
    }

    fun initState() {
        runningId?.let {
            useRunningDatabaseUseCase.getRunningById(runningId)
                .onEach { runningDTO ->
                    if (runningDTO == null) {
                        return@onEach
                    }
                    _state.value = _state.value.copy(
                        title = runningDTO.title,
                        initialPoint = runningDTO.points.firstOrNull(),
                        listOfPoints = runningDTO.points.toPersistentList(),
                        distance = runningDTO.dist,
                        listOfTags = runningDTO.tags.toPersistentList()
                    )
                }
                .launchIn(viewModelScope)
        }
    }
}